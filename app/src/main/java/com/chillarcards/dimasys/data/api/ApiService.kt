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
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @Author: Sherin Jaison
 * @Date: 25-06-2024
 * Chillar
 */

interface ApiService {

    @POST("R_login")
    suspend fun setLogin(@Body reqModel: LoginRequestModel): Response<LoginModel>
    @POST("R_verify_otp")
    suspend fun verifyOtp(
        @Body reqModel: OtpRequestModel
    ): Response<OTPModel>

    @POST("R_user")
    suspend fun getPtrHome(@Body reqModel: PatnerHomeRequestModel): Response<LandingHomeModel>

    @POST("R_recent_transactions")
    suspend fun getRecentTransactions(
        @Body reqModel: UserIDReqModel
    ): Response<RecentTransactionsResModel>

    @POST("R_products")
    suspend fun getRecentProducts(
        @Body reqModel: UserIDReqModel
    ): Response<RecentProductsResModel>

    @POST("R_billings")
    suspend fun getBillings(
        @Body reqModel: UserIDReqModel
    ): Response<BillingsResModel>

    @POST("R_money_collected")
    suspend fun getMoneyCollected(
        @Body reqModel: UserIDReqModel
    ): Response<MoneyCollectedResModel>

    @POST("R_user")
    suspend fun getSubDistWalletBalance(@Body reqModel:WalletBalanceRequestClass):Response<WalletBalanceResponseClass>

@POST("R_stm_payment_collected")
suspend fun getTotalPaymentCollect(@Body reqModel: UserIDReqModel):Response<TotalPaymentCollectResClass>

@POST("R_merchants")
suspend fun getMerchantDetails(@Body reqModel: UserIDReqModel):Response<MerchantDetailsResClass>

    @POST("C_merchant")
    suspend fun NewMerchant(@Body reqModel:NewMerchantReqClass):Response<NewMerchantResponseClass>

    @POST("C_receive_cash")
    suspend fun cashReceive(@Body reqModel:ReceiveCashRequestClass):Response<ReceiveCashResponseClass>

    @POST("R_pending_invoice")
    suspend fun pendingInvoice(@Body reqModel:PendingInvoiceReqClass):Response<PendingInvoiceResClass>

    @POST("R_cash_collections")
    suspend fun transactionHistory(@Body reqModel:TransactionHistoryReqClass):Response<TransactionHistoryResClass>

}

