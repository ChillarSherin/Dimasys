package com.chillarcards.dimasys.listener

interface ItemClickListener {
    fun onReceiveCashClick(merchantID:String)
    fun transactionHistory(merchantID: String)
}