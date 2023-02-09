package com.wayyyhoo.dev.ironman.payment.tools


object PayConstant {
    const val SCAN_TYPE = "SCAN_TYPE"

    // 协议地址
    var REGISTER_URL = ""//服务协议
    var PRIVACY_URL = ""//隐私协议
    var PACKAGE_NAME = ""//包名

    val UN_SUBSCRIPT_URL = "https://play.google.com/store/account/subscriptions"//没有已到期的订阅时，打开其他订阅管理

    val UN_SUBSCRIPT_MANAGER_URL = "https://play.google.com/store/account/subscriptions?sku=%1s&package=%2s"


}