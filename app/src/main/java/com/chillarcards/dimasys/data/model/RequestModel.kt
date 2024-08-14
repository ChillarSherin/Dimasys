package com.chillarcards.dimasys.data.model


/**
 * Created by Sherin on 25-06-2024.
 */

data class LoginRequestModel(
    val userEmail: String,
    val userPassword: String
)
data class OtpRequestModel(
    val userID: String,
    val otp: String
)
data class PatnerHomeRequestModel(
    val userID: String,
)
