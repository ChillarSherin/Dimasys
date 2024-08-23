package com.chillarcards.dimasys.modelclass

data class TransactionHistoryReqClass(
    val userID: String?,
    val merchantID: String?,
    val fromdate: String?,
    val todate: String?
)
