package com.chillarcards.dimasys.ui.others.merchant

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentCashReceiveBinding
import com.chillarcards.dimasys.databinding.FragmentOtpBinding
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale


class CashReceiveFragment : Fragment(R.layout.fragment_cash_receive) {

lateinit var binding:FragmentCashReceiveBinding
private val cashReceiveViewModel by viewModel<CashReceiveViewModel>()
    private lateinit var prefManager: PrefManager
    private val args:CashReceiveFragmentArgs by navArgs()
    private val calendar = Calendar.getInstance()
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCashReceiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = CashReceiveFragmentArgs.fromBundle(requireArguments())
        Log.d("CashReceiveFragment", "Received ID: ${args.id}")

        prefManager=PrefManager(requireContext())
        binding.myToolbar.setOnClickListener {
         findNavController().navigateUp()
        }
        cashReceiveViewModel.run {
            userID.value=prefManager.getUserId()
            merchantID.value=prefManager.getMerchantID()
            cashReceiveViewModel.pendingInvoice()
            pendingInvoiceObserver()
        }
        binding.tvBtnSubmit.setOnClickListener {
            cashReceiveViewModel.run {
                userID.value=prefManager.getUserId()
                merchantID.value=prefManager.getMerchantID()
                amount.value=binding.etAmount.text.toString()
                description.value=binding.etDescription.text.toString()
                if (userID.value.isNullOrEmpty() || merchantID.value.isNullOrEmpty() || amount.value.isNullOrEmpty() || description.value.isNullOrEmpty()){
                    Toast.makeText(requireContext(),"Fill all the fields", Toast.LENGTH_SHORT).show()
                }
                else {
                    cashReceiveViewModel.receiveCash()

                }
            }
        }




        setUpObserver()
    }

    private fun pendingInvoiceObserver(){
        try{
            cashReceiveViewModel.pendingInvoice.observe(viewLifecycleOwner){
                if (it!=null){
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let { resData->
                                when(resData.code){
                                    "200"->{
                                        binding.etPartiallyUnpaid.text=resData.details?.pendingInvoices
                                        binding.etPendingAmount.text= resData.details?.pendingAmount.toString()
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

        }
    }

    private fun setUpObserver(){
        try{
            cashReceiveViewModel.receiveCash.observe(viewLifecycleOwner){
                if (it!=null){
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let{ resData->
                               when(resData.code){
                                  "200" ->{
                                      findNavController().navigate(CashReceiveFragmentDirections.actionCashReceiveFragmentToCashReceivedSuccessfullyFragment(
                                          args.id))
                                  }

                                   "400"->{
                                       Const.shortToast(requireContext(), resData.message.toString()
                                       )
                                   }
                                   else -> Const.shortToast(requireContext(), resData.message.toString())
                               }


                            }
                        }

                        Status.LOADING->{

                        }

                        Status.ERROR->{
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }

            }



        }
        catch (e:Exception){

        }
    }






}