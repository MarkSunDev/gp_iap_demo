package com.wayyyhoo.dev.ironman.payment.init

import android.content.Context

interface IPayEventCallback {

    fun onPayWebViewEvent(context: Context, title: String, url: String)
}