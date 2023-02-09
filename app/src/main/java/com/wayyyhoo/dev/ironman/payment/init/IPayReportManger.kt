package com.wayyyhoo.dev.ironman.payment.init

import android.content.Context
import android.os.Bundle

interface IPayReportManger {
    fun reportFirebase(context: Context, name: String, bundle: Bundle)
    fun reportFirebase(name: String, bundle: Bundle)
}