package com.chillarcards.dimasys.data.model.transaction.res


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class RecentTransactionsResModel(
    val code: String?,
    val details: Details?,
    val message: String?,
    val status: String?
)