package com.chillarcards.dimasys.ui.others.money_collected

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.model.money_collected.res.MoneyCollectedResModel
import com.chillarcards.dimasys.data.model.total_billing.res.BillingsResModel
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

/**
 * Created by Deepak Dileepan on 12-07-2024.
 */
class MoneyCollectedViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _moneyCollected = MutableLiveData<Resource<MoneyCollectedResModel>?>()
    val moneyCollected: LiveData<Resource<MoneyCollectedResModel>?> get() = _moneyCollected

    var userId = MutableLiveData<String>()

    fun getPartnerRecentTransactions() {
        viewModelScope.launch(NonCancellable) {
            try {
                _moneyCollected.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getMoneyCollected(
                        userId.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _moneyCollected.postValue(Resource.success(it.body()))
                        } else {
                            _moneyCollected.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _moneyCollected.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

}