package com.chillarcards.dimasys.ui.others.merchant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProductsResModel
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.modelclass.MerchantDetailsResClass
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

class MerchantViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _merchantDetails = MutableLiveData<Resource<MerchantDetailsResClass>>()
    val merchantDetails: LiveData<Resource<MerchantDetailsResClass>?> get() = _merchantDetails

    var userId = MutableLiveData<String>()

    fun getMerchantData() {
        viewModelScope.launch(NonCancellable) {
            try {
                _merchantDetails.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getMerchantDetails(userId.value.toString()).let {
                        if (it.isSuccessful) {
                            _merchantDetails.postValue(Resource.success(it.body()))
                            Log.d("MerchantFragment", "Full API response: ${it}")

                        } else {
                            _merchantDetails.postValue(
                                Resource.error(
                                    it.errorBody().toString(),
                                    null
                                )
                            )
                        }
                    }
                } else {
                    _merchantDetails.postValue(Resource.error("No Internet Connection", null))
                }

            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)

            }
        }
    }
}