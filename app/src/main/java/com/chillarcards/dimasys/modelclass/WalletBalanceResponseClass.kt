package com.chillarcards.dimasys.modelclass

data class WalletBalanceResponseClass(
    val code:String,
    val status:String,
    val message:String,
    val details:WalletBalance
)
data class WalletBalance(
    val walletBalance:String,
    val onboarded_merchants:Int,
    val total_available_inventory:Int,
    val total_inventory_at_merchant_level:Int

)
