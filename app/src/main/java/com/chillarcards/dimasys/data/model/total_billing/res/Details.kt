package com.chillarcards.dimasys.data.model.total_billing.res


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class Details(
    @SerializedName("currentMonth")
    val currentMonth: String?,
    @SerializedName("previousMonth")
    val previousMonth: String?,
    @SerializedName("untilDate")
    val untilDate: String?
)