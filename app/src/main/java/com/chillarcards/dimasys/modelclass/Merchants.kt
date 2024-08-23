package com.chillarcards.dimasys.modelclass

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Merchants(
    val merchants: List<MerchantDetailsDataClass>?
) : Serializable
