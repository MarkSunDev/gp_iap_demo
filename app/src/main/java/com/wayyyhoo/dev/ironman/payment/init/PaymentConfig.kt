package com.wayyyhoo.dev.ironman.payment.init

class PaymentConfig {
    private var mLocalConfig: IPayDataManager? = null
    private var mCloudConfig: IPayCloudConfig? = null
    private var mReportManger: IPayReportManger? = null
    private var mEventCallback: IPayEventCallback? = null

    private var isDebug = false

    fun setLocalConfig(localConfig: IPayDataManager?) {
        mLocalConfig = localConfig
    }

    fun getLocalConfig(): IPayDataManager? {
        return mLocalConfig
    }

    fun getCloudConfig(): IPayCloudConfig? {
        return mCloudConfig
    }

    fun setCloudConfig(cloudConfig: IPayCloudConfig?) {
        this.mCloudConfig = cloudConfig
    }

    fun isDebug(): Boolean {
        return isDebug
    }

    fun setDebug(isDebug: Boolean) {
        this.isDebug = isDebug
    }


    fun getReportConfig(): IPayReportManger? {
        return mReportManger
    }

    fun setReportConfig(reportManger: IPayReportManger?) {
        this.mReportManger = reportManger
    }

    fun getEventCallback(): IPayEventCallback? {
        return mEventCallback
    }

    fun setEventCallback(callback: IPayEventCallback?) {
        this.mEventCallback = callback
    }

    class Builder {
        private var mIsDebug = false
        private var mLocalConfig: IPayDataManager? = null
        private var mCloudConfig: IPayCloudConfig? = null
        private var mReportManger: IPayReportManger? = null
        private var mEventCallback: IPayEventCallback? = null

        fun debug(isDebug: Boolean): Builder {
            mIsDebug = isDebug
            return this
        }

        fun localConfig(IPayDataManager: IPayDataManager?): Builder {
            mLocalConfig = IPayDataManager
            return this
        }

        fun cloudConfig(IPayCloudConfig: IPayCloudConfig?): Builder {
            mCloudConfig = IPayCloudConfig
            return this
        }

        fun reportConfig(IPayReportManger: IPayReportManger?): Builder {
            mReportManger = IPayReportManger
            return this
        }

        fun eventCallback(callback: IPayEventCallback): Builder {
            mEventCallback = callback
            return this
        }

        fun build(): PaymentConfig {
            val config = PaymentConfig()
            config.setDebug(mIsDebug)
            config.setLocalConfig(mLocalConfig)
            config.setCloudConfig(mCloudConfig)
            config.setReportConfig(mReportManger)
            config.setEventCallback(mEventCallback)
            return config
        }
    }

    companion object {
        //本地保存的付费数据
        const val KEY_PAID_INFO = "key_paid_info"

        //获取云控付费数据
        const val KEY_PAY_CLOUD_DATA = "pay_cloud_data"
    }
}