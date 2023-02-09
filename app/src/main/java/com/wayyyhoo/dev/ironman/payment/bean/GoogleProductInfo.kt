package com.wayyyhoo.dev.ironman.payment.bean

import com.android.billingclient.api.ProductDetails

class GoogleProductInfo(private val productDetails: ProductDetails) {

    val productTitle: String
        get() {
            return productDetails.title
        }

    val productId: String
        get() {
            return productDetails.productId
        }

    val productName: String
        get() {
            return productDetails.name
        }

    val skuToken: String
        get() {
            val offerDetails = productDetails.subscriptionOfferDetails
            if (offerDetails == null || offerDetails.isEmpty()) {
                return ""
            }
            return offerDetails[0].offerToken
        }

    val productDesc: String
        get() {
            return productDetails.productId
        }

    val productType: String
        get() {
            return productDetails.productId
        }

    val productPrice: String
    get() {
        val offerDetails = productDetails.subscriptionOfferDetails
        if (offerDetails == null || offerDetails.isEmpty()) {
            return ""
        }
        return offerDetails[0].pricingPhases.pricingPhaseList[0].formattedPrice
    }

    val googleProduct: ProductDetails
    get() {
        return productDetails
    }


}