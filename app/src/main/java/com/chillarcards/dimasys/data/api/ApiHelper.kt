package com.chillarcards.dimasys.data.api

import com.chillarcards.dimasys.data.model.OTPModel
import com.chillarcards.dimasys.data.model.*
import com.chillarcards.dimasys.data.model.money_collected.res.MoneyCollectedResModel
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProductsResModel
import com.chillarcards.dimasys.data.model.total_billing.res.BillingsResModel
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
import com.chillarcards.dimasys.modelclass.MerchantDetailsResClass
import com.chillarcards.dimasys.modelclass.NewMerchantResponseClass
import com.chillarcards.dimasys.modelclass.PendingInvoiceResClass
import com.chillarcards.dimasys.modelclass.ReceiveCashResponseClass
import com.chillarcards.dimasys.modelclass.TotalPaymentCollectResClass
import com.chillarcards.dimasys.modelclass.TransactionHistoryResClass
import com.chillarcards.dimasys.modelclass.WalletBalanceResponseClass
import retrofit2.Response

/**
 * @Author: Sherin Jaison
 * @Date: 25-06-2024
 * Chillar
 */
interface ApiHelper {

    suspend fun setLogin(
        userEmail: String,
        userPassword: String,
    ): Response<LoginModel>
//    suspend fun sendOTP(
//        mobileNumber: String,
//        userID: String,
//        token: String
//    ): Response<OTPModel>
//
    suspend fun verifyOtp(
        userID: String,
        otp: String
    ): Response<OTPModel>
    suspend fun getPtrHome(
        userID: String,
    ): Response<LandingHomeModel>

    suspend fun getPartnerRecentTransactions(
        userID: String,
    ): Response<RecentTransactionsResModel>

    suspend fun getRecentProducts(
        userID: String,
    ): Response<RecentProductsResModel>

    suspend fun getTotalBilling(
        userID: String,
    ): Response<BillingsResModel>

    suspend fun getMoneyCollected(
        userID: String,
    ): Response<MoneyCollectedResModel>

    suspend fun getSubDistWalletBalance(userID: String,):Response<WalletBalanceResponseClass>
    suspend fun getTotalPaymentCollect(userID: String):Response<TotalPaymentCollectResClass>
    suspend fun getMerchantDetails(userID: String):Response<MerchantDetailsResClass>
    suspend fun newMerchant(userID: String,merchantEmail:String, merchantName:String,
                            merchantContact:String, merchantPassword:String, country:String, merchantCode:String,
                            postalcode:String, addressLine1:String, addressLine2:String, colony:String, state:String):Response<NewMerchantResponseClass>

    suspend fun cashReceive(userID: String, merchantID:String, amount:String, description:String):Response<ReceiveCashResponseClass>
    suspend fun pendingInvoice(userID: String,merchantID: String):Response<PendingInvoiceResClass>
    suspend fun transactionHistory(userID: String,merchantID: String,fromdate:String,todate:String):Response<TransactionHistoryResClass>

}