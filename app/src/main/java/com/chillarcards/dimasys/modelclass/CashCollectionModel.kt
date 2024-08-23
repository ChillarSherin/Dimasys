package com.chillarcards.dimasys.modelclass

import java.io.Serializable

data class CashCollectionModel(
    val transactionUID:String?,
    val transactionCreatedON:String?,
    val amount:String?
) : Serializable
