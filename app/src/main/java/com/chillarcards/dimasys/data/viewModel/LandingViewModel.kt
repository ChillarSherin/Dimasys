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
class LandingViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _plandData = MutableLiveData<Resource<LandingHomeModel>?>()
    val plandData: LiveData<Resource<LandingHomeModel>?> get() = _plandData

    var userId = MutableLiveData<String>()

    fun getPhome() {
        viewModelScope.launch(NonCancellable) {
            try {
                _plandData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getPtrHome(
                        userId.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _plandData.postValue(Resource.success(it.body()))
                        } else {
                            _plandData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _plandData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

    fun clear() {
        _plandData.value = null
    }
}