package com.chillarcards.dimasys.ui.others.recent_products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProductsResModel
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

/**
 * Created by Deepak Dileepan on 12-07-2024.
 */
class RecentProductsViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _recentProducts = MutableLiveData<Resource<RecentProductsResModel>?>()
    val recentProducts: LiveData<Resource<RecentProductsResModel>?> get() = _recentProducts

    var userId = MutableLiveData<String>()

    fun getRecentProducts() {
        viewModelScope.launch(NonCancellable) {
            try {
                _recentProducts.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getRecentProducts(
                        userId.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _recentProducts.postValue(Resource.success(it.body()))
                        } else {
                            _recentProducts.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _recentProducts.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

}