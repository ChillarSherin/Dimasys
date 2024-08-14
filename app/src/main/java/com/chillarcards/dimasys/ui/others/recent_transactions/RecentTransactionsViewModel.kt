package com.chillarcards.dimasys.ui.others.recent_transactions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

/**
 * Created by Deepak Dileepan on 12-07-2024.
 */
class RecentTransactionsViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _recentTransactions = MutableLiveData<Resource<RecentTransactionsResModel>?>()
    val recentTransactions: LiveData<Resource<RecentTransactionsResModel>?> get() = _recentTransactions

    var userId = MutableLiveData<String>()

    fun getPartnerRecentTransactions() {
        viewModelScope.launch(NonCancellable) {
            try {
                _recentTransactions.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getRecentTransactions(
                        userId.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _recentTransactions.postValue(Resource.success(it.body()))
                        } else {
                            _recentTransactions.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _recentTransactions.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

}