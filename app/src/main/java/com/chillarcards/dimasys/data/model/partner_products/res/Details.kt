package com.chillarcards.dimasys.data.model.partner_products.res


import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Details(
    val recentproducts: List<RecentProduct?>?
) : Serializable