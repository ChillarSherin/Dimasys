package com.chillarcards.dimasys.data.model.money_collected.res


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class MoneyCollectedResModel(
    @SerializedName("code")
    val code: String?,
    @SerializedName("details")
    val details: Details?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)