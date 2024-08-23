package com.chillarcards.dimasys.ui.others.total_payment_collected

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.modelclass.TotalPaymentCollectResClass
import com.chillarcards.dimasys.modelclass.WalletBalanceResponseClass
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.launch
import retrofit2.http.POST

class TotalPaymentCollectedViewModel(
    private var authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
):ViewModel()

{
    private val _merchantBal=MutableLiveData<Resource<TotalPaymentCollectResClass>?>()
    val merchantBal: LiveData<Resource<TotalPaymentCollectResClass>?> get() = _merchantBal
    val userID=MutableLiveData<String>()

    fun getMerchantBalance(){
        viewModelScope.launch {
            try {
                _merchantBal.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {authRepository.getTotalPaymentCollect(userID.value.toString()).let {
                    if (it.isSuccessful){
                        _merchantBal.postValue(Resource.success(it.body()))
                    }
                    else{
                        _merchantBal.postValue(Resource.error(it.errorBody().toString(),null))
                    }

                }}

                else{
                    _merchantBal.postValue(Resource.error("No Internet Connection",null))
                }
            }

            catch (e:Exception){
                Log.e("abc_merchant","merchantBalance",e)
            }
        }

    }

}