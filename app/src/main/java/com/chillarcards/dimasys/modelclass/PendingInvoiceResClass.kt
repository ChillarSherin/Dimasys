package com.chillarcards.dimasys.modelclass

data class PendingInvoiceResClass(
    val code:String?,
    val status:String?,
    val message:String?,
    val details:PendingInvoicesDetails?
)

data class PendingInvoicesDetails(
    val pendingInvoices:String?,
    val pendingAmount:Int?,
)
