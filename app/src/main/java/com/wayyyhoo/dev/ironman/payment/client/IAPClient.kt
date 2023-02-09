package com.wayyyhoo.dev.ironman.payment.client

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.wayyyhoo.dev.ironman.payment.bean.SubscribeDetail
import com.wayyyhoo.dev.ironman.payment.tools.PayLog
import com.wayyyhoo.dev.ironman.payment.tools.ThreadUtils

class IAPClient {

    private val TAG = "GooglePayClient"
    
    private var billingClient: BillingClient? = null
    private var skuPageWakeupListener: SkuPageWakeupListener? = null
    private var selectSubscribeDetail: SubscribeDetail? = null

    fun startConnectClient(context: Context, connectListener: IAPConnectListener) {
        billingClient = BillingClient.newBuilder(context)
            .setListener { billingResult, purchases ->
                val msg = billingResult.debugMessage
                when (billingResult.responseCode) {
                    BillingClient.BillingResponseCode.OK -> {
                        PayLog.e(TAG, "购买操作的回调成功, message:$msg")
                        //查询购买的状态
                        handlePurchase(purchases)
                    }
                    else -> {
                        PayLog.e(TAG, "购买操作的回调失败:${billingResult.responseCode}, message:$msg")
                        ThreadUtils.runOnUIThread {
                            skuPageWakeupListener?.onPayFail(billingResult.responseCode)
                        }
                    }
                }
            }
            .enablePendingPurchases()
            .build()
        billingClient?.startConnection(object: BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                when (billingResult.responseCode) {
                    BillingClient.BillingResponseCode.OK -> {
                        PayLog.e(TAG, "付费链接成功")
                        ThreadUtils.runOnUIThread {
                            connectListener.onClientConnectSuccess()
                        }
                    }
                    else -> {
                        PayLog.e(TAG, "付费链接失败，reason:${billingResult.responseCode}")
                        ThreadUtils.runOnUIThread {
                            connectListener.onClientConnectFail(billingResult.responseCode)
                        }
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
                PayLog.e(TAG, "付费链接已断开，重新链接")
                ThreadUtils.runOnUIThread {
                    billingClient?.startConnection(this)
                }
            }
        })
    }

    fun querySUBSDetailList(context: Context, subDetailList: List<SubscribeDetail>, loadListener: SkuLoadListener) {
        if (billingClient == null || !billingClient!!.isReady) {
            loadListener.onLoadFail(500)
            return
        }
        val skuList = ArrayList<QueryProductDetailsParams.Product>()
        subDetailList.forEach {
            val params = QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it.productId!!)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
            skuList.add(params)
        }
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(skuList)
            .build()

        billingClient?.queryProductDetailsAsync(queryProductDetailsParams) {
                billingResult, productDetailsList ->

            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    PayLog.e(TAG, "获取商品列表成功 ${subDetailList.size} ${productDetailsList.size} ")
                    subDetailList.forEach { detail ->
                        productDetailsList.forEach { product ->
                            if (detail.productId == product.productId) {
                                detail.productDetails = product
                                detail.productPrice = getOfferDetailPrice(product)
                                detail.skuToken = getSubscriptionDetailOfferToken(product)
                            }
                        }
                    }
                    ThreadUtils.runOnUIThread {
                        loadListener.onLoadSuccess(subDetailList)
                    }
                }
                else -> {
                    PayLog.e(TAG, "获取商品列表失败，reason:${billingResult.responseCode}")
                    ThreadUtils.runOnUIThread {
                        loadListener.onLoadFail(billingResult.responseCode)
                    }
                }
            }
        }
    }

    //获取GP后台配置的商品数据中的价格
    private fun getOfferDetailPrice(product: ProductDetails): String {
        val offerDetails = product.subscriptionOfferDetails
        offerDetails?.forEach { offerDetail ->
            val phaseList = offerDetail.pricingPhases.pricingPhaseList
            phaseList.forEach {
                if (it.priceAmountMicros > 0) {
                    return it.formattedPrice
                }
            }
        }
        return ""
    }

    private fun getSubscriptionDetailOfferToken(product: ProductDetails): String {
        val offerDetails = product.subscriptionOfferDetails
        offerDetails?.forEach {
            return it.offerToken
        }
        return ""
    }

    fun submitPay(activity: Activity, detail: SubscribeDetail, listener: SkuPageWakeupListener) {
        skuPageWakeupListener = listener
        if (billingClient == null || !billingClient!!.isReady) {
            listener.onPayFail(500)
            return
        }
        selectSubscribeDetail = detail
        val productDetail = detail.productDetails
        val offerToken = detail.skuToken
        if (offerToken.isEmpty()) {
            listener.onPayFail(600)
            return
        }
        val params = ArrayList<BillingFlowParams.ProductDetailsParams>()
        //添加购买数据
        val productDetailsParams = BillingFlowParams.ProductDetailsParams
            .newBuilder()
            .setOfferToken(offerToken)
            .setProductDetails(productDetail!!)
            .build()
        params.add(productDetailsParams)
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(params)
            .build()
        //响应code 码
        val responseCode: Int = billingClient!!.launchBillingFlow(activity, billingFlowParams).responseCode
        when (responseCode) {
            //成功换起
            BillingClient.BillingResponseCode.OK -> {
                PayLog.e(TAG, "吊起成功！！！")
            }
            else -> {
                PayLog.e(TAG, "吊起失败！！！ reason:$responseCode")
                ThreadUtils.runOnUIThread {
                    listener.onPayFail(responseCode)
                }
            }
        }
    }

    /**
     * 购买完成后查询购买状态
     * 或者主动查询购买状态
     * PS：不能多次调用，有配额。所以后期考虑会放到服务端
     */
    private fun handlePurchase(purchasesList: MutableList<Purchase>?) {
        if (purchasesList == null || purchasesList.isEmpty()) {
            return
        }
        purchasesList.forEach { purchase ->
            when (purchase.purchaseState) {
                Purchase.PurchaseState.PURCHASED -> {
                    PayLog.e(TAG, "购买成功")
                    //购买成功后，需要调用确认交易
                    val params = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()
                    billingClient?.acknowledgePurchase(params, object: AcknowledgePurchaseResponseListener {
                        override fun onAcknowledgePurchaseResponse(billingResult: BillingResult) {
                            when (billingResult.responseCode) {
                                BillingClient.BillingResponseCode.OK -> {
                                    PayLog.e(TAG, "购买成功，确认购买成功！！！")
                                    ThreadUtils.runOnUIThread {
                                        skuPageWakeupListener?.onPaySuccess()
                                    }
                                }
                                else -> {
                                    PayLog.e(TAG, "购买成功，确认购买失败 ${billingResult.responseCode}")
                                    ThreadUtils.runOnUIThread {
                                        skuPageWakeupListener?.onPayFail(billingResult.responseCode)
                                    }
                                }
                            }
                        }
                    })
                }
                Purchase.PurchaseState.UNSPECIFIED_STATE -> {
                    PayLog.e(TAG, "购买失败")
                    ThreadUtils.runOnUIThread {
                        skuPageWakeupListener?.onPayFail(700)
                    }
                }
                Purchase.PurchaseState.PENDING -> {
                    PayLog.e(TAG, "购买等待付款")
                }
            }
        }
    }

    fun queryPaymentStatus(listener: PurchasesResponseListener) {
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
        billingClient?.queryPurchasesAsync(params, listener)
    }

    fun disConnectClient() {
        billingClient?.endConnection()
    }

}