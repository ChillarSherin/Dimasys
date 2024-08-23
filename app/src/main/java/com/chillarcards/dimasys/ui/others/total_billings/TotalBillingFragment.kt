package com.chillarcards.dimasys.ui.others.total_billings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.data.model.total_billing.res.BillingsResModel
import com.chillarcards.dimasys.data.model.total_billing.res.Details
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
import com.chillarcards.dimasys.databinding.FragmentTotalBillingBinding
import com.chillarcards.dimasys.databinding.NavHeaderBinding
import com.chillarcards.dimasys.ui.adapter.BillingAdapter
import com.chillarcards.dimasys.ui.adapter.RecentTransactionAdapter
import com.chillarcards.dimasys.ui.others.recent_transactions.RecentTransactionsViewModel
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class TotalBillingFragment : Fragment() {

    lateinit var binding: FragmentTotalBillingBinding
    private val recentTransactionViewModel by viewModel<TotalBillingViewModel>()
    private lateinit var prefManager: PrefManager
    private lateinit var adapter: BillingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTotalBillingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
binding.myToolbar.setOnClickListener {
    findNavController().navigateUp()
}

        recentTransactionViewModel.run {
            userId.value = prefManager.getUserId()
            recentTransactionViewModel.getPartnerRecentTransactions()
        }

        setUpObserver()
        initViews()

    }

    private fun initViews() {
        initAdapter()
        binding.apply {
            recentTotalBillingRV.adapter = adapter
        }
    }

    private fun setUpObserver() {
        try {
            recentTransactionViewModel.billings.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
//                            hideProgress()
                            it.data?.let { resData ->
                                when (resData.code) {
                                    "200" -> {
                                        analyzeResponse(resData)
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

    private fun analyzeResponse(resData: BillingsResModel) {
        adapter.submitList(mutableListOf(resData.details))
    }


    fun initAdapter() {
        adapter = BillingAdapter()
    }


}