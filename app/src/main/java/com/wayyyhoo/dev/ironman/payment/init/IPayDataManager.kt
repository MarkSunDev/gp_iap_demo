package com.wayyyhoo.dev.ironman.payment.init

interface IPayDataManager {

    fun setIntValue(key: String, value: Int)

    fun getIntValue(key: String, defValue: Int): Int

    fun setBooleanValue(key: String, value: Boolean)

    fun getBooleanValue(key: String, defValue: Boolean): Boolean

    fun setLongValue(key: String, value: Long)

    fun getLongValue(key: String, defValue: Long): Long

    fun getStringValue(key: String, defValue: String): String?

    fun setStringValue(key: String, value: String)
}