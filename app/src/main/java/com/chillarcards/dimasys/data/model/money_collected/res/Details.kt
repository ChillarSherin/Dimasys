package com.chillarcards.dimasys.data.model.money_collected.res


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class Details(
    @SerializedName("cards")
    val cards: String?,
    @SerializedName("offline")
    val offline: String?,
    @SerializedName("oxxo")
    val oxxo: String?,
    @SerializedName("transfer")
    val transfer: String?
)