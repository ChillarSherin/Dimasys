package com.chillarcards.dimasys.ui.sales

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.modelclass.WalletBalanceResponseClass
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.launch

class SalesTeamMemberViewModel(private val authRepository: AuthRepository,
                               private val networkHelper: NetworkHelper
): ViewModel()
{
private val _walletBalance= MutableLiveData<Resource<WalletBalanceResponseClass>?>()
val walletBalance: LiveData<Resource<WalletBalanceResponseClass>?> get() = _walletBalance
var userID= MutableLiveData<String>()

fun getWalletBalance(){
    viewModelScope.launch {
        try {
            _walletBalance.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()){authRepository.getSubDistWalletBalance(userID.value.toString()).let {
                if (it.isSuccessful){
                    _walletBalance.postValue(Resource.success(it.body()))
                }
                else{
                    _walletBalance.postValue(Resource.error(it.errorBody().toString(),null))
                }
            }

            }

            else{
                _walletBalance.postValue(Resource.error("No Internet Connection",null))
            }

        }
        catch (e:Exception){
            Log.e("abc_wallet", "WalletBalance: ", e)
        }
    }
}
}