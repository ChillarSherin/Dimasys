package com.chillarcards.dimasys.modelclass

import java.io.Serializable

data class MerchantDetailsDataClass(
    val merchantID: String?,
    val name: String?,
    val contact: String?,
    val email: String?,
    val mid: String?,
    val walletID: String?,
    val balance: String?
) : Serializable
