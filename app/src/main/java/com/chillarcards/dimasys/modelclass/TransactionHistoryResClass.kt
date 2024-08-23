package com.chillarcards.dimasys.modelclass

import java.io.Serializable

data class TransactionHistoryResClass(
    val code: String?,
    val status: String?,
    val message: String?,
    val details: TransactionDetails?
) : Serializable

data class TransactionDetails(
    val collectionData: List<CashCollectionModel>?
) : Serializable

