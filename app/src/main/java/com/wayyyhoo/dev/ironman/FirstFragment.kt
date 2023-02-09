package com.wayyyhoo.dev.ironman

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResponseListener
import com.wayyyhoo.dev.ironman.databinding.FragmentFirstBinding
import com.wayyyhoo.dev.ironman.payment.PaymentDelegate
import com.wayyyhoo.dev.ironman.payment.SkuProductLoadListener
import com.wayyyhoo.dev.ironman.payment.bean.GoogleProductInfo
import com.wayyyhoo.dev.ironman.payment.bean.SubscribeDetail
import com.wayyyhoo.dev.ironman.payment.client.IAPConnectListener
import com.wayyyhoo.dev.ironman.payment.client.ILoadIAPDataCallback
import com.wayyyhoo.dev.ironman.payment.client.SkuLoadListener
import com.wayyyhoo.dev.ironman.payment.client.SkuPageWakeupListener
import com.wayyyhoo.dev.ironman.payment.tools.PayLog

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    val TAG = "PaymentActivity"

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PaymentDelegate.connectIAPClient(context!!, object : IAPConnectListener {
            override fun onClientConnectSuccess() {
                queryProductList()
            }

            override fun onClientConnectFail(responseCode: Int) {
                TODO("Not yet implemented")
            }

        })
        initIAPData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            if (activity != null) {
                openPaymentDialog()
            }
        }

        binding.textviewPayTitle.text = "当前售价未知"

    }

    private var mSubscribeDetailList: List<SubscribeDetail>? = null

    private fun initIAPData() {
        PaymentDelegate.loadIAPDataOnThread(context, object : ILoadIAPDataCallback {
            override fun onLoadIAPData(list: List<SubscribeDetail>?) {
                refreshUI(list)
            }

        })
    }

    private fun queryProductList() {
        if (mSubscribeDetailList == null) {
            return
        }
        PaymentDelegate.querySkuDetails(context!!, mSubscribeDetailList!!, object :
            SkuLoadListener {
            override fun onLoadSuccess(products: List<SubscribeDetail>?) {
                PayLog.e(TAG, "onLoadSuccess")
                refreshUI(products)
            }

            override fun onLoadFail(response: Int) {
                PayLog.e(TAG, "onLoadFail $response")
            }
        })
    }

    private fun refreshUI(products: List<SubscribeDetail>?) {
        if (products != null) {
            mSubscribeDetailList = products
            val size = products.size
            if (size > 0) {
                binding.textviewPayTitle.text = products[1].productTitle
            }
        }
    }

    private fun openPaymentDialog(): Boolean {
        if (mSubscribeDetailList == null || mSubscribeDetailList!!.isEmpty()) {
            PayLog.e(TAG, "商品不存在，无法购买")
            return false
        }
        var subscribeDetail: SubscribeDetail? = null
        mSubscribeDetailList?.forEach {
            if (it.isItemSelect) {
                subscribeDetail = it
            }
        }
        if (subscribeDetail == null) {
            return false
        }
        PaymentDelegate.submitPay(activity!!, subscribeDetail!!, object : SkuPageWakeupListener {

            override fun onPaySuccess() {
                PayLog.e(TAG, "商品购买成功！！！")
                subscribeDetail!!.paid = true
            }

            override fun onPayFail(response: Int) {
                PayLog.e(TAG, "商品购买失败， 错误码：$response")
                Toast.makeText(activity, "购买失败", Toast.LENGTH_SHORT).show()
            }
        })
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        PaymentDelegate.disConnect()
    }
}