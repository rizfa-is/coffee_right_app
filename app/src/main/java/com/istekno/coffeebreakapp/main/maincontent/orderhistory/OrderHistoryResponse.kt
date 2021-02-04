package com.istekno.coffeebreakapp.main.maincontent.orderhistory

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderHistoryResponse(val success: Boolean, val message: String, val data: List<Data>): Parcelable {

    @Parcelize
    data class Data(
        @SerializedName("od_id") val orderDetailId: Int,
        @SerializedName("cs_id") val customerId: Int,
        @SerializedName("cs_name") val customerName: String,
        @SerializedName("cs_email") val customerEmail: String,
        @SerializedName("cs_phone") val customerPhone: String,
        @SerializedName("dv_dt") val deliveryType: String,
        @SerializedName("dv_address") val deliveryAddress: String,
        @SerializedName("od_total_price_before_tax") val priceBeforeTax: Int,
        @SerializedName("od_transaction_id") val transactionId: String,
        @SerializedName("od_total_price") val totalPrice: Int,
        @SerializedName("od_status") val orderDetailStatus: String,
        @SerializedName("od_payment_method") val orderPayment: String,
        @SerializedName("od_tax") val orderTax: Int,
        @SerializedName("od_created_at") val orderCreated: String,
        @SerializedName("od_updated_at") val orderUpdated: String,
        @SerializedName("product_order") val productOrder: List<Product>
    ) : Parcelable

    @Parcelize
    data class Product(
        @SerializedName("pr_id") val productId: Int,
        @SerializedName("pr_name") val productName: String,
        @SerializedName("pr_image") val productImage: String,
        @SerializedName("or_price") val orderPrice: Int,
        @SerializedName("od_status") val orderDetailStatus: String,
        @SerializedName("or_amount") val orderAmount: String
    ) : Parcelable
}