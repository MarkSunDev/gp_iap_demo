package com.wayyyhoo.dev.ironman.payment.bean

import android.os.Parcel
import android.os.Parcelable
import com.android.billingclient.api.ProductDetails
import com.google.gson.annotations.SerializedName

/**
 * Created by sunxiaoyang@cmcm.com
 * 2023/1/3 17:17
 */
class SubscribeDetail(): Parcelable {

    //GP SKU
    var productDetails: ProductDetails? = null

    @SerializedName("product_id")
    var productId: String? = null

    @SerializedName("product_title")
    var productTitle: String? = null

    @SerializedName("product_desc")
    var productDesc: String? = null

    @SerializedName("product_price")
    var productPrice: String? = null

    @SerializedName("item_select")
    var isItemSelect = false

    var skuToken: String = ""

    /**
     * 是否已经付费
     */
    var paid: Boolean = false

    /**
     * 无符号价格
     */
    var priceAmount: String?  = ""

    constructor(parcel: Parcel) : this() {
        productId = parcel.readString()
        productTitle = parcel.readString()
        productDesc = parcel.readString()
        priceAmount = parcel.readString()
        isItemSelect = parcel.readByte() != 0.toByte()
        paid = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productId)
        parcel.writeString(productTitle)
        parcel.writeString(productDesc)
        parcel.writeString(priceAmount)
        parcel.writeByte(if (isItemSelect) 1 else 0)
        parcel.writeByte(if (paid) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubscribeDetail> {
        override fun createFromParcel(parcel: Parcel): SubscribeDetail {
            return SubscribeDetail(parcel)
        }

        override fun newArray(size: Int): Array<SubscribeDetail?> {
            return arrayOfNulls(size)
        }
    }
}