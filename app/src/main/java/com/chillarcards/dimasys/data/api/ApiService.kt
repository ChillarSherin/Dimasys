package com.chillarcards.dimasys.data.api

import com.chillarcards.dimasys.data.model.*
import com.chillarcards.dimasys.data.model.money_collected.res.MoneyCollectedResModel
import com.chillarcards.dimasys.data.model.partner_products.req.UserIDReqModel
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProductsResModel
import com.chillarcards.dimasys.data.model.total_billing.res.BillingsResModel
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
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
    suspend fun setLogin(
        @Body reqModel: LoginRequestModel
    ): Response<LoginModel>
    @POST("R_verify_otp")
    suspend fun verifyOtp(
        @Body reqModel: OtpRequestModel
    ): Response<OTPModel>
    @POST("R_user")
    suspend fun getPtrHome(
        @Body reqModel: PatnerHomeRequestModel
    ): Response<LandingHomeModel>

    @POST("R_partner_recent_transactions")
    suspend fun getRecentTransactions(
        @Body reqModel: UserIDReqModel
    ): Response<RecentTransactionsResModel>

    @POST("R_partner_products")
    suspend fun getRecentProducts(
        @Body reqModel: UserIDReqModel
    ): Response<RecentProductsResModel>

    @POST("R_partner_billings")
    suspend fun getBillings(
        @Body reqModel: UserIDReqModel
    ): Response<BillingsResModel>

    @POST("R_partner_money_collected")
    suspend fun getMoneyCollected(
        @Body reqModel: UserIDReqModel
    ): Response<MoneyCollectedResModel>

}