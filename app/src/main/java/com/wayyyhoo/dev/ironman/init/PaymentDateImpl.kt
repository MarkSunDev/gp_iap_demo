package com.wayyyhoo.dev.ironman.init

import com.wayyyhoo.dev.ironman.payment.init.IPayDataManager

class PaymentDateImpl : IPayDataManager {

    override fun setIntValue(key: String, value: Int) {
    }

    override fun getIntValue(key: String, defValue: Int): Int {
        return 0
    }

    override fun setBooleanValue(key: String, value: Boolean) {
    }

    override fun getBooleanValue(key: String, defValue: Boolean): Boolean {
        return false
    }

    override fun setLongValue(key: String, value: Long) {
    }

    override fun getLongValue(key: String, defValue: Long): Long {
        return 0L
    }

    override fun setStringValue(key: String, value: String) {
    }

    override fun getStringValue(key: String, defValue: String): String? {
        return ""
    }

}