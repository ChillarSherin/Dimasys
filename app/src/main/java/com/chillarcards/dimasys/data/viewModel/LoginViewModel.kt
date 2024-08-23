package com.chillarcards.dimasys.data.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.model.*
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

/**
 * @Author: Sherin Jaison
 * @Date: 25-06-2024
 * Chillar
 */
class LoginViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _loginData = MutableLiveData<Resource<LoginModel>?>()
    val loginData: LiveData<Resource<LoginModel>?> get() = _loginData
    private val _otpVerifyData = MutableLiveData<Resource<OTPModel>?>()
    val otpVerifyData: LiveData<Resource<OTPModel>?> get() = _otpVerifyData

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var otp = MutableLiveData<String>()
    var userId = MutableLiveData<String>()

    fun clearOtpVerifyData() {
        _otpVerifyData.value = null
        _loginData.value=null
    }



    fun setlogin() {
        viewModelScope.launch(NonCancellable) {
            try {
                _loginData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) { authRepository.setLogin(email.value.toString(), password.value.toString()).let {
                        if (it.isSuccessful) {
                            _loginData.postValue(Resource.success(it.body()))
                        } else {
                            _loginData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _loginData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }
    fun verifyOtp() {
        viewModelScope.launch(NonCancellable) {
            try {
                _otpVerifyData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.verifyOtp(userId.value.toString(), otp.value.toString()).let {
                        if (it.isSuccessful) {
                            _otpVerifyData.postValue(Resource.success(it.body()))
                        } else {
                            _otpVerifyData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _otpVerifyData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }


    fun clear() {
        _loginData.value = null
        _otpVerifyData.value = null
    }
}