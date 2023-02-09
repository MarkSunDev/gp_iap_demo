package com.wayyyhoo.dev.ironman.payment

import androidx.annotation.Nullable
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase


interface PayResultListener {

    fun onPurchasesUpdated(result: BillingResult,@Nullable purchases: List<Purchase>?)

    fun onProductDetailsSus(@Nullable list: List<ProductDetails>)

    fun onConsumeSus(@Nullable purchaseToken: String)
}