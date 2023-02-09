package com.wayyyhoo.dev.ironman.payment.init

import com.wayyyhoo.dev.ironman.payment.PaymentDelegate


object PaymentCloudManager {

    private var mCloudManger: IPayCloudConfig? = PaymentDelegate.getPaymentConfig().getCloudConfig()

    fun getIntValue(key: String?, defaultVal: Int): Int {
        return mCloudManger?.getIntValue(key, defaultVal) ?: defaultVal
    }

    fun getLongValue(key: String?, defValue: Long): Long {
        return mCloudManger?.getLongValue(key, defValue) ?: defValue
    }

    fun getBooleanValue(key: String?, defValue: Boolean): Boolean {
        return mCloudManger?.getBooleanValue(key, defValue) ?: defValue
    }

    fun getStringValue(key: String?, defValue: String?): String? {
        return mCloudManger?.getStringValue(key, defValue) ?: defValue
    }

}