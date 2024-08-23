package com.chillarcards.dimasys.ui.others.merchant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.modelclass.TransactionHistoryResClass
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

class TransactionHistoryViewModel(
    private var networkHelper: NetworkHelper,
    private var authRepository: AuthRepository
):ViewModel()
{
    val _transactionDetails = MutableLiveData<Resource<TransactionHistoryResClass>>()
    val transactionDetails: LiveData<Resource<TransactionHistoryResClass>?> get() = _transactionDetails

    var userID=MutableLiveData<String>()
   var merchantID=MutableLiveData<String>()
  var fromdate=MutableLiveData<String>()
  var todate=MutableLiveData<String>()

    fun transactionHistory(){
        viewModelScope.launch(NonCancellable) {
            try {
                _transactionDetails.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {authRepository.transactionHistory(userID.value.toString(),
                    merchantID.value.toString(),fromdate.value.toString(),todate.value.toString()).let {
                        if (it.isSuccessful){
                            _transactionDetails.postValue(Resource.success(it.body()))
                        }
                    else{
                        _transactionDetails.postValue(Resource.error(it.errorBody().toString(),null))
                    }

                }}
                else {
                    _transactionDetails.postValue(Resource.error("No Internet Connection", null))
                }

            }
            catch (e:Exception){
                Log.e("TransactionHistoryViewModel", "Exception in TransactionHistory: ", e)
            }
        }
    }

}