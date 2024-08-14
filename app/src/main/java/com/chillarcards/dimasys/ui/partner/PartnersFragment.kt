package com.chillarcards.dimasys.ui.partner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.data.viewModel.*
import com.chillarcards.dimasys.databinding.FragmentHomeBinding
import com.chillarcards.dimasys.ui.interfaces.DrawerController
import com.chillarcards.dimasys.ui.interfaces.IAdapterViewUtills
import com.chillarcards.dimasys.utills.CommonDBaseModel
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class PartnersFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentHomeBinding
    private val landingViewModel by viewModel<LandingViewModel>()
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        setupToolbar()
        setUpClickListener()
        landingViewModel.run {
            userId.value = prefManager.getUserId()
            getPhome()
        }
        setUpObserver()
    }

    private fun setUpClickListener() {

        binding.apply {
            recentProdPart.setOnClickListener {
                getRecentProducts()
            }

            recentTransPart.setOnClickListener {
                getRecentTransactions()
            }

            txtTotalBill.setOnClickListener {
                getTotalBilling()
            }

            moneyCollect.setOnClickListener {
                getMoneyCollect()
            }
        }

    }

    private fun getMoneyCollect() {
        findNavController().navigate(PartnersFragmentDirections.actionHomeFragmentToMoneyCollectedFragment())
    }

    private fun getTotalBilling() {
        findNavController().navigate(PartnersFragmentDirections.actionHomeFragmentToTotalBillingFragment())
    }

    private fun getRecentTransactions() {
        findNavController().navigate(PartnersFragmentDirections.actionHomeFragmentToRecentTransactionsFragment())
    }

    private fun getRecentProducts() {
        findNavController().navigate(PartnersFragmentDirections.actionHomeFragmentToRecentProductsFragment())
    }

    private fun setupToolbar() {
        binding.toolbar.toolbarMenu.setOnClickListener {
           // openDrawer()
            Log.d("PartnersFragment", "Toolbar menu clicked")
            val parentFragment = requireParentFragment().parentFragmentManager.findFragmentById(R.id.action_homeFragment_to_homeFragment)
            if (parentFragment is DrawerController) {
                Const.shortToast(requireContext(),"1")
                parentFragment.openDrawer()
            } else {
                Const.shortToast(requireContext(),"2")
                Log.e("PartnersFragment", "Parent fragment is not implementing DrawerController")
            }

        }
    }
    private fun setUpObserver() {
        try {
            landingViewModel.plandData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { resData ->
                                when (resData.code) {
                                    "200" -> {

                                        binding.onbrdDistri.text= resData.details.onboarded_distributors.toString()
                                        binding.onbrdSaleTeam.text= resData.details.onboarded_sales_team.toString()
                                        binding.onbrdMerchant.text= resData.details.onboarded_merchants.toString()
                                        binding.onbrdTtlInv.text= resData.details.total_available_inventory.toString()
                                        binding.ttlIvtryDist.text= resData.details.total_inventory_at_distributor_level.toString()
                                        //TODO not getting from API
                                        binding.ttlInvtRetailerLvl.text= resData.details.total_inventory_at_distributor_level.toString()

                                    }
                                    "400" -> {
                                        Const.shortToast(requireContext(), resData.message)
                                    }
                                    else -> Const.shortToast(requireContext(), resData.message)
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }
    }

    private fun showProgress() {
        binding.loginProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.loginProgress.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        Log.d("abc_mob", "onStop: ")
       // mobileViewModel.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("abc_mob", "onDestroy: ")
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {

    }
}
