package com.wayyyhoo.dev.ironman.payment.client

interface IAPConnectListener {
    fun onClientConnectSuccess()
    fun onClientConnectFail(responseCode: Int)
}