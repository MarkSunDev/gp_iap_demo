package com.wayyyhoo.dev.ironman.payment.init

import android.content.Context
import android.os.Bundle
import com.wayyyhoo.dev.ironman.payment.PaymentDelegate

object PaymentReportManager {

    private var mAdReportManager: IPayReportManger? = PaymentDelegate.getPaymentConfig().getReportConfig()

    fun reportFirebase(context: Context, name: String, bundle: Bundle) {
        mAdReportManager?.reportFirebase(context, name, bundle)
    }

    fun reportFirebase(name: String, bundle: Bundle) {
        mAdReportManager?.reportFirebase(name, bundle)
    }
}