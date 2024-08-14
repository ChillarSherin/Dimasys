package com.chillarcards.dimasys.data.model


/**
 * Created by Sherin on 25-06-2024.
 */

data class LoginModel(
    val code: String,
    val status: String,
    val message: String,
    val details: LoginDetail
)
data class LoginDetail(
    val userID: String,
    val userName: String,
    val userPhone: String,
    val userType: String
)
