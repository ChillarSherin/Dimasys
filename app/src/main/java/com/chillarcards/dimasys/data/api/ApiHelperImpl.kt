package com.chillarcards.dimasys.data.api

import com.chillarcards.dimasys.data.model.*
import com.chillarcards.dimasys.data.model.money_collected.res.MoneyCollectedResModel
import com.chillarcards.dimasys.data.model.partner_products.req.UserIDReqModel
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProductsResModel
import com.chillarcards.dimasys.data.model.total_billing.res.BillingsResModel
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
import com.chillarcards.dimasys.modelclass.MerchantDetailsResClass
import com.chillarcards.dimasys.modelclass.NewMerchantReqClass
import com.chillarcards.dimasys.modelclass.NewMerchantResponseClass
import com.chillarcards.dimasys.modelclass.PendingInvoiceReqClass
import com.chillarcards.dimasys.modelclass.PendingInvoiceResClass
import com.chillarcards.dimasys.modelclass.ReceiveCashRequestClass
import com.chillarcards.dimasys.modelclass.ReceiveCashResponseClass
import com.chillarcards.dimasys.modelclass.TotalPaymentCollectResClass
import com.chillarcards.dimasys.modelclass.TransactionHistoryReqClass
import com.chillarcards.dimasys.modelclass.TransactionHistoryResClass
import com.chillarcards.dimasys.modelclass.WalletBalanceRequestClass
import com.chillarcards.dimasys.modelclass.WalletBalanceResponseClass
import retrofit2.Response

/**
 * @Author: Sherin Jaison
 * @Date: 25-06-2024
 * Chillar
 */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {


    override suspend fun setLogin(userEmail: String, userPassword: String): Response<LoginModel> = apiService.setLogin(LoginRequestModel(userEmail, userPassword))

    override suspend fun verifyOtp(
        userId: String, otp: String): Response<OTPModel> = apiService.verifyOtp(OtpRequestModel(userId, otp))

    override suspend fun getPtrHome(userId: String): Response<LandingHomeModel> = apiService.getPtrHome(PatnerHomeRequestModel(userId))

    override suspend fun getPartnerRecentTransactions(userID: String): Response<RecentTransactionsResModel> =
        apiService.getRecentTransactions(UserIDReqModel(userID))

    override suspend fun getRecentProducts(userID: String): Response<RecentProductsResModel>  =
        apiService.getRecentProducts(UserIDReqModel(userID))

    override suspend fun getTotalBilling(userID: String): Response<BillingsResModel> =
        apiService.getBillings(UserIDReqModel(userID))

    override suspend fun getMoneyCollected(userID: String): Response<MoneyCollectedResModel> =
        apiService.getMoneyCollected(UserIDReqModel(userID))

    override suspend fun getSubDistWalletBalance(userID: String): Response<WalletBalanceResponseClass> =apiService.getSubDistWalletBalance(WalletBalanceRequestClass(userID))

    override suspend fun getTotalPaymentCollect(userID: String): Response<TotalPaymentCollectResClass> =apiService.getTotalPaymentCollect(UserIDReqModel(userID))
    override suspend fun getMerchantDetails(userID: String): Response<MerchantDetailsResClass> =apiService.getMerchantDetails(UserIDReqModel(userID))
    override suspend fun newMerchant(
        userID: String,
        merchantEmail: String,
        merchantName: String,
        merchantContact: String,
        merchantPassword: String,
        country: String,
        merchantCode: String,
        postalcode: String,
        addressLine1: String,
        addressLine2: String,
        colony: String,
        state: String
    ): Response<NewMerchantResponseClass> =apiService.NewMerchant(NewMerchantReqClass(userID,merchantEmail,merchantName,
        merchantContact,merchantPassword,country,merchantCode,postalcode,addressLine1,addressLine2,colony,state))

    override suspend fun cashReceive(
        userID: String,
        merchantID: String,
        amount: String,
        description: String): Response<ReceiveCashResponseClass> =apiService.cashReceive(ReceiveCashRequestClass(userID,merchantID,amount,description))

    override suspend fun pendingInvoice(userID: String, merchantID: String): Response<PendingInvoiceResClass> =apiService.pendingInvoice(
        PendingInvoiceReqClass(userID, merchantID)
    )

    override suspend fun transactionHistory(
        userID: String,
        merchantID: String,
        fromdate: String,
        todate: String
    ): Response<TransactionHistoryResClass> =apiService.transactionHistory(TransactionHistoryReqClass(
        userID,merchantID,fromdate,todate))


}