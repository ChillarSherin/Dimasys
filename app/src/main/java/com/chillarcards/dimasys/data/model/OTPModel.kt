package com.chillarcards.dimasys.data.model


/**
 * Created by Sherin on 25-06-2024.
 */

data class OTPModel(
    val code: String,
    val status: String,
    val message: String,
    val details: OtpDetail
)

data class OtpDetail(
    val userID: String,
    val userName: String
)
