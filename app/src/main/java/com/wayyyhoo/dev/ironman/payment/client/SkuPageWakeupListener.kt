package com.wayyyhoo.dev.ironman.payment.client

interface SkuPageWakeupListener {
    fun onPaySuccess()
    fun onPayFail(response: Int)
}