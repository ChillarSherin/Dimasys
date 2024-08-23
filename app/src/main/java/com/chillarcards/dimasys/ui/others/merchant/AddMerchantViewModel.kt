package com.chillarcards.dimasys.ui.others.merchant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.dimasys.data.repository.AuthRepository
import com.chillarcards.dimasys.modelclass.NewMerchantResponseClass
import com.chillarcards.dimasys.utills.NetworkHelper
import com.chillarcards.dimasys.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

class AddMerchantViewModel(
    private var authRepository: AuthRepository,
    private var networkHelper: NetworkHelper
):ViewModel()
{
    private var _newMerchant=MutableLiveData<Resource<NewMerchantResponseClass>>()
    val newMerchant:LiveData<Resource<NewMerchantResponseClass>> get() = _newMerchant

    val userID=MutableLiveData<String>()
    val merchantEmail=MutableLiveData<String>()
    val merchantName=MutableLiveData<String>()
    val merchantContact=MutableLiveData<String>()
    val merchantPassword=MutableLiveData<String>()
    val country=MutableLiveData<String>()
    val merchantCode=MutableLiveData<String>()
    val postalcode=MutableLiveData<String>()
    val addressLine1=MutableLiveData<String>()
    val addressLine2=MutableLiveData<String>()
    val colony=MutableLiveData<String>()
    val state=MutableLiveData<String>()

     fun getNewMerchant(){
        viewModelScope.launch (NonCancellable){
            try {
                _newMerchant.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()){authRepository.newMerchant(
                    userID.value.toString(),merchantEmail.value.toString(), merchantName.value.toString(), merchantContact.value.toString(),
                    merchantPassword.value.toString(),country.value.toString(),merchantCode.value.toString(),postalcode.value.toString(),
                    addressLine1.value.toString(),addressLine2.value.toString(), colony.value.toString(),state.value.toString()).let {

                    val rawResponse = it.errorBody()?.string() ?: "No response body"
                    Log.d("AddMerchantViewModel1", "Raw Response: $rawResponse")

                        if (it.isSuccessful){
                            Log.d("AddMerchantViewModel1", "Response successful: ${it.body()}")
                            _newMerchant.postValue(Resource.success(it.body()))
                        }
                        else {
                            Log.e("AddMerchantViewModel1", "Response error: ${it.errorBody().toString()}")
                            _newMerchant.postValue(Resource.error(it.errorBody().toString(), null))
                        }

                }}
                else {
                    _newMerchant.postValue(Resource.error("No Internet Connection", null))
                }
            }
            catch(e:Exception){
                Log.e("AddMerchantViewModel", "Exception in getNewMerchant: ", e)
            }
        }

    }


}