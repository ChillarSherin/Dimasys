package com.chillarcards.dimasys.ui.others.merchant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentAddMerchantBinding
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddMerchantFragment : Fragment(R.layout.fragment_add_merchant) {
lateinit var binding:FragmentAddMerchantBinding
private val addMerchantViewModel by viewModel<AddMerchantViewModel>()
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentAddMerchantBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        binding.myToolbar.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSubmit.setOnClickListener {
            addMerchantViewModel.run {
                userID.value=prefManager.getUserId()
                merchantEmail.value=binding.etEmail.text.toString()
                merchantName.value=binding.etMerchantName.text.toString()
                merchantContact.value=binding.etContact.text.toString()
                merchantPassword.value=binding.etPassword.text.toString()
                country.value=binding.etCountry.text.toString()
                merchantCode.value=binding.etMerchantCode.text.toString()
                postalcode.value=binding.etPostalCode.text.toString()
                addressLine1.value=binding.etAddressLIneOne.text.toString()
                addressLine2.value=binding.etAddressLIneTwo.text.toString()
                colony.value=binding.etColony.text.toString()
                state.value=binding.etState.text.toString()
                Log.d("AddMerchantViewModel", "userID: ${userID.value}")
                Log.d("AddMerchantViewModel", "merchantEmail: ${merchantEmail.value}")
                Log.d("AddMerchantViewModel", "merchantName: ${merchantName.value}")
                Log.d("AddMerchantViewModel", "merchantContact: ${merchantContact.value}")
                Log.d("AddMerchantViewModel", "merchantPassword: ${merchantPassword.value}")
                Log.d("AddMerchantViewModel", "country: ${country.value}")
                Log.d("AddMerchantViewModel", "merchantCode: ${merchantCode.value}")
                Log.d("AddMerchantViewModel", "postalcode: ${postalcode.value}")
                Log.d("AddMerchantViewModel", "addressLine1: ${addressLine1.value}")
                Log.d("AddMerchantViewModel", "addressLine2: ${addressLine2.value}")
                Log.d("AddMerchantViewModel", "colony: ${colony.value}")
                Log.d("AddMerchantViewModel", "state: ${state.value}")
                if (userID.value.isNullOrEmpty()|| merchantEmail.value.isNullOrEmpty()|| merchantName.value.isNullOrEmpty()
                    || merchantContact.value.isNullOrEmpty() || merchantPassword.value.isNullOrEmpty() || country.value.isNullOrEmpty() ||
                    merchantCode.value.isNullOrEmpty() || postalcode.value.isNullOrEmpty() || addressLine1.value.isNullOrEmpty() ||
                    addressLine2.value.isNullOrEmpty() || colony.value.isNullOrEmpty() || state.value.isNullOrEmpty())
                {
                    Toast.makeText(requireContext(),"Fill all the fields",Toast.LENGTH_SHORT).show()
                }
                else {
                    addMerchantViewModel.getNewMerchant()
                }
            }
        }
        setUpObserve()
    }

    private fun setUpObserve(){
        try{
            addMerchantViewModel.newMerchant.observe(viewLifecycleOwner){
                if (it!=null){
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let {resData->
                               when(resData.code){
                                   "200"->{
                                       findNavController().navigate(AddMerchantFragmentDirections.actionAddMerchantFragmentToAddedNewMerchantFragment())
                                   }

                                   "400"->{
                                       Const.shortToast(requireContext(), resData.message.toString()
                                       )
                                   }
                                   else -> Const.shortToast(requireContext(), resData.message.toString())
                               }
                            }
                        }
                        Status.ERROR->{
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                        Status.LOADING->{

                        }
                    }
                }
            }
        }
        catch (e:Exception){
            Log.e("abc_otp", "setUpObserver: ", e)
        }
    }


}