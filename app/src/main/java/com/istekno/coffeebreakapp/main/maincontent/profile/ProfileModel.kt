package com.istekno.coffeebreakapp.main.maincontent.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileModel(
    val customerId: Int,
    val accountId: Int,
    val accountName: String?,
    val accountEmail: String?,
    val accountPhone: String?,
    val accountGender: String?,
    val accountBirthday: String?,
    val accountAddress: String?,
    val accountImage: String?
) : Parcelable