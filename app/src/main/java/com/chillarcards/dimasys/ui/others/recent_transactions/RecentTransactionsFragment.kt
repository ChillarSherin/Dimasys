package com.chillarcards.dimasys.ui.others.recent_transactions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
import com.chillarcards.dimasys.databinding.FragmentRecentTransactionsBinding
import com.chillarcards.dimasys.ui.adapter.RecentTransactionAdapter
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecentTransactionsFragment : Fragment() {

    lateinit var binding: FragmentRecentTransactionsBinding
//    val args: RecentTransactionsFragmentArgs by navArgs()
    private val recentTransactionViewModel by viewModel<RecentTransactionsViewModel>()
    private lateinit var prefManager: PrefManager
    private lateinit var adapter: RecentTransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecentTransactionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())


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
            recentTransactionsRV.adapter = adapter
        }
    }

    private fun setUpObserver() {
        try {
            recentTransactionViewModel.recentTransactions.observe(viewLifecycleOwner) {
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

    private fun analyzeResponse(resData: RecentTransactionsResModel) {
        adapter.submitList(resData.details?.recentTransactions ?: emptyList())
    }


    fun initAdapter() {
        adapter = RecentTransactionAdapter()
    }


}