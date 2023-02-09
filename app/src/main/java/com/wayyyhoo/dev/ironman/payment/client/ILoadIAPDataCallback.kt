package com.wayyyhoo.dev.ironman.payment.client

import com.wayyyhoo.dev.ironman.payment.bean.SubscribeDetail

interface ILoadIAPDataCallback {
    fun onLoadIAPData(list: List<SubscribeDetail>?)
}