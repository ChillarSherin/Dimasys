package com.chillarcards.dimasys.data.api

import com.chillarcards.dimasys.data.model.*
import com.chillarcards.dimasys.data.model.money_collected.res.MoneyCollectedResModel
import com.chillarcards.dimasys.data.model.partner_products.req.UserIDReqModel
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProductsResModel
import com.chillarcards.dimasys.data.model.total_billing.res.BillingsResModel
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
import retrofit2.Response

/**
 * @Author: Sherin Jaison
 * @Date: 25-06-2024
 * Chillar
 */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {


    override suspend fun setLogin(
        userEmail: String,
        userPassword: String
    ): Response<LoginModel> = apiService.setLogin(
        LoginRequestModel(userEmail, userPassword)
    )

    override suspend fun verifyOtp(
        userId: String,
        otp: String
    ): Response<OTPModel> = apiService.verifyOtp(
        OtpRequestModel(userId, otp)
    )

    override suspend fun getPtrHome(
        userId: String
    ): Response<LandingHomeModel> = apiService.getPtrHome(
        PatnerHomeRequestModel(userId)
    )

    override suspend fun getPartnerRecentTransactions(userID: String): Response<RecentTransactionsResModel> =
        apiService.getRecentTransactions(UserIDReqModel(userID))

    override suspend fun getRecentProducts(userID: String): Response<RecentProductsResModel>  =
        apiService.getRecentProducts(UserIDReqModel(userID))

    override suspend fun getTotalBilling(userID: String): Response<BillingsResModel> =
        apiService.getBillings(UserIDReqModel(userID))

    override suspend fun getMoneyCollected(userID: String): Response<MoneyCollectedResModel> =
        apiService.getMoneyCollected(UserIDReqModel(userID))


}