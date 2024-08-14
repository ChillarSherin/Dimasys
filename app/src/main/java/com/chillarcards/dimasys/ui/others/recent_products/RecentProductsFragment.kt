package com.chillarcards.dimasys.ui.others.recent_products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProductsResModel
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransactionsResModel
import com.chillarcards.dimasys.databinding.FragmentMobileBinding
import com.chillarcards.dimasys.databinding.FragmentRecentProductsBinding
import com.chillarcards.dimasys.databinding.FragmentRecentTransactionsBinding
import com.chillarcards.dimasys.ui.adapter.RecentProductsAdapter
import com.chillarcards.dimasys.ui.adapter.RecentTransactionAdapter
import com.chillarcards.dimasys.ui.others.recent_transactions.RecentTransactionsViewModel
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecentProductsFragment : Fragment() {

    lateinit var binding: FragmentRecentProductsBinding
    //    val args: RecentTransactionsFragmentArgs by navArgs()
    private val recentTransactionViewModel by viewModel<RecentProductsViewModel>()
    private lateinit var prefManager: PrefManager
    private lateinit var adapter: RecentProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecentProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())


        recentTransactionViewModel.run {
            userId.value = prefManager.getUserId()
            recentTransactionViewModel.getRecentProducts()
        }

        setUpObserver()
        initViews()

    }

    private fun initViews() {
        initAdapter()
        binding.apply {
            recentProductRV.adapter = adapter
        }
    }

    private fun setUpObserver() {
        try {
            recentTransactionViewModel.recentProducts.observe(viewLifecycleOwner) {
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

    private fun analyzeResponse(resData: RecentProductsResModel) {
        adapter.submitList(resData.details?.recentproducts ?: emptyList())
    }


    fun initAdapter() {
        adapter = RecentProductsAdapter()
    }

}