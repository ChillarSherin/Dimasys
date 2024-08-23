package com.chillarcards.dimasys.data.repository

import com.chillarcards.dimasys.data.api.ApiHelper

/**
 * @Author: Sherin Jaison
 * @Date: 25-06-2024
 * Chillar
 */
class AuthRepository(private val apiHelper: ApiHelper) {

    suspend fun setLogin(userEmail: String, userPassword: String) =
        apiHelper.setLogin(userEmail, userPassword)
    suspend fun verifyOtp(userId: String, otp: String) =
        apiHelper.verifyOtp(userId, otp)
    suspend fun getPtrHome(userId: String) =
        apiHelper.getPtrHome(userId)

    suspend fun getRecentTransactions(userId: String) =
        apiHelper.getPartnerRecentTransactions(userId)

    suspend fun getRecentProducts(userId: String) =
        apiHelper.getRecentProducts(userId)

    suspend fun getBillings(userId: String) =
        apiHelper.getTotalBilling(userId)

    suspend fun getMoneyCollected(userId: String) =
        apiHelper.getMoneyCollected(userId)

    suspend fun getSubDistWalletBalance(userID: String)=apiHelper.getSubDistWalletBalance(userID)

    suspend fun getTotalPaymentCollect(userID: String)=apiHelper.getTotalPaymentCollect((userID))

    suspend fun getMerchantDetails(userID: String) =apiHelper.getMerchantDetails(userID)

    suspend fun newMerchant(userID: String,merchantEmail:String, merchantName:String,
                            merchantContact:String, merchantPassword:String, country:String, merchantCode:String,
                            postalcode:String, addressLine1:String, addressLine2:String, colony:String, state:String) =apiHelper.newMerchant(userID,
        merchantEmail, merchantName,  merchantContact, merchantPassword,country, merchantCode, postalcode, addressLine1, addressLine2, colony,state   )

suspend fun cashReceive(userID: String, merchantID:String, amount:String, description:String)=
    apiHelper.cashReceive(userID,merchantID, amount, description )
    suspend fun pendingInvoice(userID: String,merchantID: String)=apiHelper.pendingInvoice(userID,merchantID)
    suspend fun transactionHistory(userID: String,merchantID: String,fromdate:String,todate:String)=apiHelper.transactionHistory(userID,merchantID,fromdate,todate)
}