package com.wayyyhoo.dev.ironman

import android.app.Application
import android.content.Context
import com.wayyyhoo.dev.ironman.init.PayEventCallbackImpl
import com.wayyyhoo.dev.ironman.init.PaymentCloudImpl
import com.wayyyhoo.dev.ironman.init.PaymentDateImpl
import com.wayyyhoo.dev.ironman.init.PaymentReportImpl
import com.wayyyhoo.dev.ironman.payment.PaymentDelegate
import com.wayyyhoo.dev.ironman.payment.init.PaymentConfig

class MainApplication: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        initPayment(this)

    }
    private fun initPayment(context: Context) {
        val config = PaymentConfig.Builder()
            .cloudConfig(PaymentCloudImpl())
            .localConfig(PaymentDateImpl())
            .reportConfig(PaymentReportImpl())
            .eventCallback(PayEventCallbackImpl())
            .build()
        PaymentDelegate.initSDK(context, config)
    }
}