package com.wayyyhoo.dev.ironman.payment

import com.wayyyhoo.dev.ironman.payment.bean.GoogleProductInfo

interface SkuProductLoadListener {
    fun onProductUpdate(products: List<GoogleProductInfo>)
}