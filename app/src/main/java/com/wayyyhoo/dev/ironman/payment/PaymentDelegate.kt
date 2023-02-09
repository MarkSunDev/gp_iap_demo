package com.wayyyhoo.dev.ironman.payment

import android.app.Activity
import android.content.Context
import com.wayyyhoo.dev.ironman.payment.bean.SubscribeDetail
import com.wayyyhoo.dev.ironman.payment.init.PaymentCloudManager
import com.wayyyhoo.dev.ironman.payment.init.PaymentConfig
import com.wayyyhoo.dev.ironman.payment.init.PaymentDataManager
import com.wayyyhoo.dev.ironman.payment.tools.PayConstant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wayyyhoo.dev.ironman.payment.client.*
import com.wayyyhoo.dev.ironman.payment.tools.ThreadUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object PaymentDelegate {

    private lateinit var mPaymentConfig: PaymentConfig
    private var mPaymentClient: IAPClient? = null

    /**
     * 付费模块初始化
     */
    fun initSDK(context: Context?, config: PaymentConfig) {
        mPaymentConfig = config
    }

    fun getPaymentConfig(): PaymentConfig {
        return mPaymentConfig;
    }

    private fun loadLocalIAPData(context: Context?): String? {
        if (context == null) {
            return ""
        }
        val stringBuilder = StringBuilder()
        var inputStream: InputStream? = null
        try {
            inputStream = context.resources.assets.open("iap_data.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            var jsonLine: String?
            while (reader.readLine().also { jsonLine = it } != null) {
                stringBuilder.append(jsonLine)
            }
            reader.close()
            isr.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    /**
     * 加载SKU数据
     * 线程异步
     */
    fun loadIAPDataOnThread(context: Context?, callback: ILoadIAPDataCallback?) {
        ThreadUtils.runOnThreadPool {
            try {
                val localData = loadLocalIAPData(context)
                val typeToken = object : TypeToken<ArrayList<SubscribeDetail>>() {}.type
                val detailList = Gson().fromJson<ArrayList<SubscribeDetail>>(localData, typeToken)
                detailList.forEach {
                    val detail = getPaidSubscribeDetail()
                    if (detail != null && it.productId.equals(detail.productId)) {
                        it.paid = true
                        val list = ArrayList<SubscribeDetail>()
                        list.add(it)
                        callback?.onLoadIAPData(list)
                        return@runOnThreadPool
                    }
                }
                callback?.onLoadIAPData(detailList)
            } catch (e: Exception) {
            }
        }
    }

    /**
     * 连接付费服务,一般在页面的onCreate()执行
     */
    fun connectIAPClient(context: Context, connectListener: IAPConnectListener) {
        mPaymentClient = IAPClient()
        mPaymentClient?.startConnectClient(context, connectListener)
    }

    /**
     * 断开付费服务，一般在页面的onDestroy()执行
     */
    fun disConnect() {
        mPaymentClient?.disConnectClient()
    }

    /**
     * 通过本地数据获取GP中的SKU数据，并进行合并
     */
    fun querySkuDetails(
        context: Context,
        subscribeDetailList: List<SubscribeDetail>,
        loadListener: SkuLoadListener
    ) {
        mPaymentClient?.querySUBSDetailList(context, subscribeDetailList, object : SkuLoadListener {

            override fun onLoadSuccess(products: List<SubscribeDetail>?) {
                loadListener.onLoadSuccess(products)
            }

            override fun onLoadFail(response: Int) {
                loadListener.onLoadFail(response)
            }
        })
    }

    /**
     * 获取订阅管理的URL
     */
    fun getPaymentManagerUrl(context: Context): String {
        val info = getPaidSubscribeDetail()
        if (info != null && info.paid) {
            val sku = info.productId
            val packageName = PayConstant.PACKAGE_NAME
            if (!sku.isNullOrEmpty() && packageName.isNotEmpty()) {
                //有到期的订阅，打开该订阅管理页面
                return String.format(PayConstant.UN_SUBSCRIPT_MANAGER_URL, sku, packageName)
            }
        }
        return PayConstant.UN_SUBSCRIPT_URL
    }


    fun submitPay(
        activity: Activity,
        subscribeDetail: SubscribeDetail,
        skuPageWakeupListener: SkuPageWakeupListener
    ) {
        mPaymentClient?.submitPay(activity, subscribeDetail, skuPageWakeupListener)
    }


    /**
     * 付费成功后保存数据到本地
     */
    fun updatePaymentState(detail: SubscribeDetail) {
        try {
            val infoStr = Gson().toJson(detail)
            PaymentDataManager.getInstance().setStringValue(PaymentConfig.KEY_PAID_INFO, infoStr)
        } catch (e: Exception) {
        }
    }

    /**
     * 获取付费后的本地数据详情
     */
    fun getPaidSubscribeDetail(): SubscribeDetail? {
        try {
            val infoStr =
                PaymentDataManager.getInstance().getStringValue(PaymentConfig.KEY_PAID_INFO, "")
            if (infoStr.isEmpty()) {
                return null
            }
            return Gson().fromJson(infoStr, SubscribeDetail::class.java)
        } catch (e: Exception) {
        }
        return null
    }

    /**
     * 获取是否已付费
     */
    fun getPurchasePaid(): Boolean {
        val info = getPaidSubscribeDetail()
        if (info != null) {
            return info.paid
        }
        return false
    }
}