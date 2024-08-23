package com.chillarcards.dimasys.ui.others.merchant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentMerchantBinding
import com.chillarcards.dimasys.listener.ItemClickListener
import com.chillarcards.dimasys.modelclass.MerchantDetailsResClass
import com.chillarcards.dimasys.ui.adapter.MerchantAdapter
import com.chillarcards.dimasys.ui.others.recent_products.RecentProductsViewModel
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class MerchantFragment : Fragment(R.layout.fragment_merchant), ItemClickListener {

lateinit var binding:FragmentMerchantBinding
    private val merchantViewModel by viewModel<MerchantViewModel>()
    private lateinit var prefManager: PrefManager
    private lateinit var adapter: MerchantAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMerchantBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        binding.myToolbar.setOnClickListener {
            findNavController().navigate(MerchantFragmentDirections.actionMerchantFragmentToSubDistributorFragment())
        }
        binding.merchantLayout.setOnClickListener {
            findNavController().navigate(MerchantFragmentDirections.actionMerchantFragmentToAddMerchantFragment())
        }
        merchantViewModel.run {
            userId.value=prefManager.getUserId()
            merchantViewModel.getMerchantData()
        }

        setUpObserver()
        initViews()

    }

    fun initAdapter(){
        adapter= MerchantAdapter(this)
    }

    private fun initViews(){
        initAdapter()
        binding.myToolbar.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.apply {
            rvMerchant.adapter=adapter
        }

    }

    private fun setUpObserver() {
        try {
            merchantViewModel.merchantDetails.observe(viewLifecycleOwner) {
                Log.d("MerchantFragment", "Observer triggered with status: ${it?.status}")
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            Log.d("MerchantFragment", "Response data: ${it.data}")
//                            hideProgress()
                            it.data?.let { resData ->
                                when (resData.code) {
                                    "200" -> {
                                        analyzeResponse(resData)
                                        Log.d("MerchantFragment", "Merchant details processed")
                                        val merchantID=resData.details?.merchants?.firstOrNull()?.merchantID
                                        prefManager.setMerchantID(merchantID.toString())
                                    }

                                    "400" -> {
                                        Const.shortToast(requireContext(), resData.message ?: "")
                                    }

                                    else -> Const.shortToast(
                                        requireContext(),
                                        resData.message ?: ""
                                    )
                                }
                            }
                        }

                        Status.LOADING -> {
//                            showProgress()
                        }

                        Status.ERROR -> {
                            Log.e("MerchantFragment", "Error: ${it.message}")

//                            hideProgress()
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }

    }

    private fun analyzeResponse(resData:MerchantDetailsResClass){
        val merchantDetails = resData.details?.merchants ?: emptyList()
        Log.d("MerchantFragment", "Merchant details size: ${resData.details?.merchants?.size}")
        adapter.submitList(merchantDetails)

    }

    override fun onReceiveCashClick(merchantID: String) {
        prefManager.setMerchantID(merchantID)
        try{
            findNavController().navigate(MerchantFragmentDirections.actionMerchantFragmentToCashReceiveFragment())
        }catch (e: Exception){

        }

    }

    override fun transactionHistory(merchantID: String) {
        prefManager.setMerchantID(merchantID)
        findNavController().navigate(MerchantFragmentDirections.actionMerchantFragmentToTransactionHistoryFragment())
    }


}