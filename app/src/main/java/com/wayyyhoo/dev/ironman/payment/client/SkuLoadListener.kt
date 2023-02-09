package com.wayyyhoo.dev.ironman.payment.client

import com.wayyyhoo.dev.ironman.payment.bean.SubscribeDetail

interface SkuLoadListener {
    fun onLoadSuccess(products: List<SubscribeDetail>?)
    fun onLoadFail(response: Int)
}