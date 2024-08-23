package com.chillarcards.dimasys.modelclass

data class TotalPaymentCollectResClass(
    val code:String,
    val status:String,
    val message:String,
    val details:MerchantBalance
)
data class MerchantBalance(
    val merchant:String
)
