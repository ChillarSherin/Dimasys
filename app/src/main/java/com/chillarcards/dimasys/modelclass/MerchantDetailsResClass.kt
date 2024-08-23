package com.chillarcards.dimasys.modelclass

import java.io.Serializable

data class MerchantDetailsResClass(
    val code: String?,
    val status: String?,
    val message: String?,
    val details: Merchants?
) : Serializable

