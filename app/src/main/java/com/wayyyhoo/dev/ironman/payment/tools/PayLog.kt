package com.wayyyhoo.dev.ironman.payment.tools

import android.util.Log

object PayLog {

    var isDebug = true

    fun i(tag: String, msg: String) {
        if(isDebug) {
            Log.i(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if(isDebug) {
            Log.e(tag, msg)
        }
    }
}