package com.chillarcards.dimasys.utills

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PrefManager(_context: Context) {

    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "dimasys"

        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
        private const val USER_ID = "USERID"
        private const val MERCHANT_ID="MERCHANTID"
        private const val USER_TYPE = "USERTYPE"

        // shared pref mode
        private const val PRIVATE_MODE = Context.MODE_PRIVATE
    }

    private val pref: SharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private val editor: SharedPreferences.Editor = pref.edit()


    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGGED_IN, false)
    }

    fun setIsLoggedIn(value: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, value)
        editor.commit()
    }
    fun getUserId(): String {
        return pref.getString(USER_ID, "") ?: ""
    }
    fun setUserId(value: String) {
        editor.putString(USER_ID, value)
        editor.commit()
    }

    fun clearAll() {
        editor.clear()
        editor.apply()
        editor.commit()
    }

    fun clearField(keyName: String) {
        editor.remove(keyName).apply()
    }

    fun clearUserId() {
        editor.remove(USER_ID)
        editor.apply()
    }

    fun getMerchantID(): String {
        val merchantId = pref.getString(MERCHANT_ID, "") ?: ""
        Log.d("PrefManager", "Retrieved MerchantID: $merchantId")
        return pref.getString(MERCHANT_ID, "") ?: ""
    }
    fun setMerchantID(value: String) {
        Log.d("PrefManager", "Setting MerchantID: $value")
        editor.putString(MERCHANT_ID, value)
        editor.apply()
    }
    fun setUserType(value: String) {
        Log.d("PrefManager", "Saving userType: $value")
        editor.putString(USER_TYPE, value).apply()
    }

    fun getUserType(): String {
        val userType = pref.getString(USER_TYPE, "") ?: ""
        Log.d("PrefManager", "Retrieved userType: $userType")
        return userType
    }

}
