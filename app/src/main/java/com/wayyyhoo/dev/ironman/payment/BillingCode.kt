package com.wayyyhoo.dev.ironman.payment

object BillingCode {
    /**
     * 处理过程中出现用户计费错误。
     * 可能发生此错误的示例：
     * - 用户设备上的 Play 商店应用已过期。
     * - 用户位于不受支持的国家/地区。
     * - 用户是企业用户，他们的企业管理员已禁止用户进行购买。
     * - Google Play 无法通过用户的付款方式收费
     * 如果导致错误的条件已更改（例如，企业用户的管理员已允许为组织购买），则让用户重试可能会成功。
     * 常量值：3 (0x00000003)
     */
    const val BILLING_UNAVAILABLE   = "BILLING_UNAVAILABLE"

    /**
     * 错误使用 API 导致的错误。
     * 可能发生此错误的示例：
     *  - 参数无效，例如在需要时提供空的产品列表
     *  - 应用程序配置错误，例如未签署应用程序或在清单中没有必要的权限。
     *  常量值：5 (0x00000005)
     */
    const val DEVELOPER_ERROR       = "DEVELOPER_ERROR"

    /**
     * 这是 Google Play 内部错误，可能是暂时性的，也可能是由于处理过程中的意外情况造成的。
     * 对于这种情况，您可以自动重试（例如使用指数回退），如果问题仍然存在，请联系 Google Play。
     * 如果在用户交互期间发生重试，请注意重试多长时间。
     * 常量值：6 (0x00000006)
     */
    const val ERROR                 = "ERROR"

    /**
     * 当前设备上的 Play 商店不支持请求的功能。
     *
     * 如果您的应用想要在尝试使用该功能之前检查是否支持该功能，
     * 您的应用可以调用该功能 BillingClient.isFeatureSupported(String) 来检查该功能是否受支持。
     * 有关可以支持的功能类型的列表，请参阅BillingClient.FeatureType。
     * 例如：调用API前，
     * BillingClient.showInAppMessages(Activity, InAppMessageParams, InAppMessageResponseListener)
     * 可以调用featureType判断是否支持。
     * BillingClient.isFeatureSupported(String)BillingClient.FeatureType.IN_APP_MESSAGING
     * 常数值：-2 (0xfffffffe)
     */
    const val FEATURE_NOT_SUPPORTED   = "FEATURE_NOT_SUPPORTED"

    /**
     * 购买失败，因为该项目已被拥有。
     * 如果在检查最近的购买后仍出现此错误，则可能是由于 Play 在设备上缓存的过时购买信息。
     * 当您收到此错误时，缓存应该得到更新
     */
    const val ITEM_ALREADY_OWNED   = "ITEM_ALREADY_OWNED"

    /**
     * 请求的项目操作失败，因为它不归用户所有。
     * 例如，如果您正在尝试消费某个商品，如果更新后的购买信息显示该商品已被消费，您现在可以忽略该错误。
     */
    const val ITEM_NOT_OWNED   = "ITEM_NOT_OWNED"

    /**
     * 无法购买请求的产品。
     * 请确保产品在用户所在国家/地区可用。如果您最近更改了国家/地区可用性并且仍然收到此错误，则可能是因为传播延迟。
     * 常量值：4 (0x00000004)
     */
    const val ITEM_UNAVAILABLE   = "ITEM_UNAVAILABLE"

    /**
     * 该应用未通过 Google Play 结算库连接到 Play 商店服务。
     * 可能发生此错误的示例：
     * - 当您的应用程序仍在运行并且图书馆失去连接时，Play 商店可能已经在后台更新。
     * - BillingClient.startConnection(BillingClientStateListener)从未被调用或尚未完成。
     * 由于此状态是暂时的，您的应用程序应自动重试（例如，使用指数退避）以从此错误中恢复。
     * 如果在用户交互期间发生重试，请注意重试多长时间
     * 重试应该会BillingClient.startConnection(BillingClientStateListener)在您收到此代码之后或一段时间后立即调用 。
     *
     * 常数值：-1 (0xffffffff)
     */
    const val SERVICE_DISCONNECTED   = "SERVICE_DISCONNECTED"

    /**
     * 在 Google Play 响应之前请求已达到最大超时时间。
     * 由于此状态是暂时的，您的应用程序应自动重试（例如，使用指数退避）以从此错误中恢复。
     * 如果在用户交互期间发生重试，请注意重试多长时间。
     *
     * 常数值：-3 (0xfffffffd)
     */
    const val SERVICE_TIMEOUT   = "SERVICE_TIMEOUT"

    /**
     * 该服务当前不可用。
     * 由于此状态是暂时的，您的应用程序应自动重试（例如，使用指数退避）以从此错误中恢复。
     * 如果在用户交互期间发生重试，请注意重试多长时间
     * 这也可能是由于用户没有活动的网络连接。
     *
     * 常量值：2 (0x00000002)
     */
    const val SERVICE_UNAVAILABLE   = "SERVICE_UNAVAILABLE"

    /**
     * 交易被用户取消。
     */
    const val USER_CANCELED   = "USER_CANCELED"


}