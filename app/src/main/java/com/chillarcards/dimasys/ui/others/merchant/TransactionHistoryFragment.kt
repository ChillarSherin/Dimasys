package com.chillarcards.dimasys.ui.others.merchant

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentTransactionHistoryfragmentBinding
import com.chillarcards.dimasys.modelclass.TransactionHistoryResClass
import com.chillarcards.dimasys.ui.adapter.TransactionHistoryAdapter
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date
import java.util.Locale

class TransactionHistoryFragment : Fragment(R.layout.fragment_transaction_historyfragment) {
private lateinit var binding:FragmentTransactionHistoryfragmentBinding
private lateinit var adapter: TransactionHistoryAdapter
private val transactionHistoryViewModel by viewModel<TransactionHistoryViewModel>()
    private lateinit var prefManager: PrefManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding=FragmentTransactionHistoryfragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager= PrefManager(requireContext())

        binding.myToolbar.setOnClickListener {
            findNavController().navigate(TransactionHistoryFragmentDirections.actionTransactionHistoryFragmentToMerchantFragment())
        }

        binding.etMerchantName.setOnClickListener {
            datePickerDialog(binding.etMerchantName, binding.etToDate)
        }

        binding.etToDate.setOnClickListener {
            datePickerDialog(binding.etMerchantName, binding.etToDate)
        }
        binding.tvSearch.setOnClickListener {
            transactionHistoryViewModel.run {
                userID.value = prefManager.getUserId()
                merchantID.value = prefManager.getMerchantID()
                fromdate.value = binding.etMerchantName.text.toString()
                todate.value = binding.etToDate.text.toString()
                if (fromdate.value.isNullOrEmpty()|| todate.value.isNullOrEmpty()){
                  Toast.makeText(requireContext(),"Select a date range to check transaction histroy",Toast.LENGTH_SHORT).show()
                }
                else{
                    transactionHistoryViewModel.transactionHistory()
                }

            }
        }

        transactionHistory()
        initViews()
    }

    private fun initViews(){
        initAdapter()
        binding.apply {
            recentProductRV.adapter=adapter
        }

    }
    fun initAdapter(){
        adapter= TransactionHistoryAdapter()
    }

    fun transactionHistory(){
        try{
            transactionHistoryViewModel.transactionDetails.observe(viewLifecycleOwner){
                if (it!=null){
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let { resData->
                                when(resData.code){
                                    "200"->{
                                        analyzeResponse(resData)
                                    }
                                    "400"->{
                                        Log.e("TransactionHistoryFragment", "400: ${it.message}")
                                        Const.shortToast(requireContext(), resData.message.toString()
                                        )
                                    }

                                    else -> Const.shortToast(requireContext(), resData.message.toString())
                                }
                            }
                        }

                        Status.ERROR->{
                            Log.e("TransactionHistoryFragment", "Error: ${it.message}")
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                        Status.LOADING->{

                        }
                    }
                }
            }
        }
        catch(e:Exception){

        }

    }

    private fun analyzeResponse(resData: TransactionHistoryResClass){
        Log.d("TransactionHistoryFragment", "API Response: $resData")
        val transactionDetails = resData.details?.collectionData?: emptyList()
        Log.d("TransactionHistoryFragment", "transactions details size: ${transactionDetails.size}")
        adapter.submitList(transactionDetails)

    }

    private fun datePickerDialog(startDateField: TextInputEditText, endDateField: TextInputEditText) {
        // Creating a MaterialDatePicker builder for selecting a date range
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("Select a date range")

        // Building the date picker dialog
        val datePicker = builder.build()

        // Handling the date range selection
        datePicker.addOnPositiveButtonClickListener { selection ->
            // Retrieving the selected start and end dates
            val startDate = selection.first
            val endDate = selection.second

            // Formatting the selected dates as strings
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val startDateString = sdf.format(Date(startDate))
            val endDateString = sdf.format(Date(endDate))

            // Displaying the start date in the startDateField
            startDateField.text = Editable.Factory.getInstance().newEditable(startDateString)

            // Displaying the end date in the endDateField
            endDateField.text = Editable.Factory.getInstance().newEditable(endDateString)
        }

        // Showing the date picker dialog
        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.etMerchantName.text?.clear()
        binding.etToDate.text?.clear()

        // Clear the adapter data
        adapter.submitList(emptyList())
        transactionHistoryViewModel.apply {
            userID.value = null
            merchantID.value = null
            fromdate.value = null
            todate.value = null
            _transactionDetails.value = null
        }
    }



}