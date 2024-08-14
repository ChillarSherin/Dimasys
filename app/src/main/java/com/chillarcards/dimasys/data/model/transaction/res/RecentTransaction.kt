package com.chillarcards.dimasys.data.model.transaction.res


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class RecentTransaction(
    val amount: String?,
    val date: String?,
    val receiver: String?,
    val sender: String?
)