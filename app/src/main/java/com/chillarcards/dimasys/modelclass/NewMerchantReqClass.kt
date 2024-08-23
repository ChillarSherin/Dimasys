package com.chillarcards.dimasys.modelclass

data class NewMerchantReqClass(
    val userID: String?,
    val merchantEmail: String?,
    val merchantName: String?,
    val merchantContact: String?,
    val merchantPassword: String?,
    val country: String?,
    val merchantCode: String?,
    val postalcode: String?,
    val addressLine1: String?,
    val addressLine2: String?,
    val colony: String?,
    val state: String?
)

