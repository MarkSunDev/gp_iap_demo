package com.wayyyhoo.dev.ironman.init

import com.wayyyhoo.dev.ironman.payment.init.IPayCloudConfig


class PaymentCloudImpl: IPayCloudConfig {

    override fun getIntValue(key: String?, defaultVal: Int): Int {
        return 0
    }

    override fun getLongValue(key: String?, defValue: Long): Long {
        return 0L
    }

    override fun getBooleanValue(key: String?, defValue: Boolean): Boolean {
        return false
    }

    override fun getStringValue(key: String?, defValue: String?): String {
        return ""
    }
}