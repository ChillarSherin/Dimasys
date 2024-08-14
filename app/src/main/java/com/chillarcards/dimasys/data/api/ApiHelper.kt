package com.chillarcards.dimasys.data.api

import com.chillarcards.dimasys.data.model.OTPModel
import com.chillarcards.dimasys.data.model.*
import com.chillarcards.dimasys.data.model.money_collected.res.MoneyCollectedResModel
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProductsResModel
import com.chillarcards.dimasys.data.model.total_billing.res.BillingsResModel
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
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

}