package com.wayyyhoo.dev.ironman.payment.init

import com.wayyyhoo.dev.ironman.payment.PaymentDelegate

/**
 * AdSDK 数据保存类
 */
class PaymentDataManager {

    private var mServiceDataManager: IPayDataManager?  = PaymentDelegate.getPaymentConfig().getLocalConfig()

    private object HOLDER {
        val instance = PaymentDataManager()
    }

    companion object {
        fun getInstance() = HOLDER.instance
    }

    fun setIntValue(key: String, value: Int) {
        mServiceDataManager?.setIntValue(key, value)
    }

    fun getIntValue(key: String, defValue: Int): Int {
        return mServiceDataManager?.getIntValue(key, defValue) ?: defValue
    }

    fun setBooleanValue(key: String, value: Boolean) {
        mServiceDataManager?.setBooleanValue(key, value)
    }

    fun getBooleanValue(key: String, defValue: Boolean): Boolean {
        return mServiceDataManager?.getBooleanValue(key, defValue) ?: defValue

    }

    fun setLongValue(key: String, value: Long) {
        mServiceDataManager?.setLongValue(key, value)
    }

    fun getLongValue(key: String, defValue: Long): Long {
        return mServiceDataManager?.getLongValue(key, defValue) ?: defValue
    }

    fun getStringValue(key: String, defValue: String): String {
        return mServiceDataManager?.getStringValue(key, defValue) ?: defValue
    }

    fun setStringValue(key: String, value: String) {
        mServiceDataManager?.setStringValue(key, value)
    }
}