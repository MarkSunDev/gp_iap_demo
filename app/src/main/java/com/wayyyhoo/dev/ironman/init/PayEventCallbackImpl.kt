package com.wayyyhoo.dev.ironman.init

import android.content.Context
import com.wayyyhoo.dev.ironman.payment.init.IPayEventCallback

class PayEventCallbackImpl : IPayEventCallback {

    override fun onPayWebViewEvent(context: Context, title: String, url: String) {
//        WebActivity.startActivity(context, title, url)
    }
}