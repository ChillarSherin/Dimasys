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

}