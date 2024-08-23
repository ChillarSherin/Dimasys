package com.chillarcards.dimasys.ui.others.merchant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.modelclass.PendingInvoiceResClass
import com.chillarcards.dimasys.modelclass.ReceiveCashResponseClass
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

class CashReceiveViewModel(
    private var authRepository: AuthRepository,
    private var networkHelper: NetworkHelper
):ViewModel()

{
    private val _pendingInvoice=MutableLiveData<Resource<PendingInvoiceResClass>>()
    val pendingInvoice:LiveData<Resource<PendingInvoiceResClass>?> get() = _pendingInvoice

   private val _receiveCash=MutableLiveData<Resource<ReceiveCashResponseClass>>()
    val receiveCash:LiveData<Resource<ReceiveCashResponseClass>?> get() = _receiveCash

    val userID=MutableLiveData<String>()
    val merchantID=MutableLiveData<String>()
    val amount=MutableLiveData<String>()
    val description=MutableLiveData<String>()

    fun pendingInvoice(){
        viewModelScope.launch(NonCancellable) {
            try {
               _pendingInvoice.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()){authRepository.pendingInvoice(userID.value.toString(),merchantID.value.toString()).let {
                    if (it.isSuccessful){
                        _pendingInvoice.postValue(Resource.success(it.body()))
                    }
                    else{
                        _pendingInvoice.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }}
                else {
                    _pendingInvoice.postValue(Resource.error("No Internet Connection", null))
                }
            }
            catch (e:Exception){
                Log.e("CashReceiveViewModel", "Exception in pendingInvoice: ", e)
            }
        }
    }

     fun receiveCash(){
        viewModelScope.launch (NonCancellable){
            try{
                _receiveCash.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()){authRepository.cashReceive(userID.value.toString(),
                    merchantID.value.toString(),amount.value.toString(),description.value.toString()).let {
                        if (it.isSuccessful){
                            _receiveCash.postValue(Resource.success(it.body()))
                        }
                    else{
                            _receiveCash.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }}

                else {
                    _receiveCash.postValue(Resource.error("No Internet Connection", null))
                }
            }
            catch (e:Exception){
                Log.e("CashReceiveViewModel", "Exception in cashReceive: ", e)
            }
        }
    }


}