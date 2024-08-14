package com.chillarcards.dimasys.data.model.transaction.res


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import java.io.Serializable

data class Details(
    val recentTransactions: List<RecentTransaction?>?
) : Serializable