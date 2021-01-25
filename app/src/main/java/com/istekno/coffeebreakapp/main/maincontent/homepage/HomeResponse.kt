package com.istekno.coffeebreakapp.main.maincontent.homepage

import com.google.gson.annotations.SerializedName

data class ProductResponse(val success: Boolean, val message: String, val data: List<Product>?) {
    data class Product(@SerializedName("pr_id") val prID: Int,
                       @SerializedName("dc_id") val dcID: Int,
                       @SerializedName("pr_name") val prName: String,
                       @SerializedName("pr_desc") val prDesc: String,
                       @SerializedName("pr_unit_price") val prUnitPrice: Int,
                       @SerializedName("pr_image") val prImage: Int,
                       @SerializedName("pr_category") val prCategory: Int,
                       @SerializedName("pr_day_start_deliv") val prDayStart: Int,
                       @SerializedName("pr_day_end_deliv") val prDayEnd: Int,
                       @SerializedName("pr_time_start_deliv") val prTimeStart: Int,
                       @SerializedName("pr_time_end_deliv") val prTimeEnd: Int)
}
