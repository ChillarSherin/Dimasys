package com.chillarcards.dimasys.ui.sales

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentSalesTeamsupervisorBinding
import com.chillarcards.dimasys.utills.CommonDBaseModel
import com.chillarcards.dimasys.ui.interfaces.IAdapterViewUtills
import com.chillarcards.dimasys.ui.partner.PartnersBaseFragment
import com.chillarcards.dimasys.ui.subdistributer.SubDistributerFragmentDirections
import com.chillarcards.dimasys.ui.subdistributer.SubDistributerViewModel
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class SalesTeamManagerFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentSalesTeamsupervisorBinding
    private val salesTeamMemberViewModel by viewModel<SalesTeamMemberViewModel>()
    private lateinit var prefManager: PrefManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSalesTeamsupervisorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        val pInfo =
            activity?.let { activity?.packageManager!!.getPackageInfo(it.packageName, PackageManager.GET_ACTIVITIES) }
        //  val versionName = pInfo?.versionName //Version Name
        setToolUi()
        septUpclick()
//        binding.bookingViewAll.setOnClickListener {
//            try {
//                findNavController().navigate(
//                    HomeFragmentDirections.actionHomeFragmentToBookingFragment(
//                    )
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
        salesTeamMemberViewModel.run {
            userID.value=prefManager.getUserId()
            salesTeamMemberViewModel.getWalletBalance()
        }
        setUpObserver()
    }

    private fun septUpclick(){
        binding.products.setOnClickListener {
            getRecentProducts()
        }
        binding.transcation.setOnClickListener {
            getRecentTransactions()
        }

        binding.totalBill.setOnClickListener {
            getTotalBilling()
        }
        binding.moneyCollect.setOnClickListener {
            getMoneyCollect()
        }
        binding.totalPayCollect.setOnClickListener {
            getTotalPayCollect()
        }
    }

    private fun getRecentProducts() {
        findNavController().navigate(SalesTeamManagerFragmentDirections.actionSalesTeamsuperFragmentToRecentProductsFragment())
    }

    private fun getRecentTransactions(){
        findNavController().navigate(SalesTeamManagerFragmentDirections.actionSalesTeamsuperFragmentToRecentTransactionsFragment2())
    }

    private fun getTotalBilling(){
        findNavController().navigate(SalesTeamManagerFragmentDirections.actionSalesTeamsuperFragmentToTotalBillingFragment2())
    }
    private fun getMoneyCollect(){
    findNavController().navigate(R.id.moneyCollectedFragment3)
}
    private fun getTotalPayCollect(){
        findNavController().navigate(R.id.totalPaymentCollectedFragment)
    }
    private fun setUpObserver(){
        try {
            salesTeamMemberViewModel.walletBalance.observe(viewLifecycleOwner){
                if (it!=null){
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let { resData ->
                                when (resData.code) {
                                    "200" -> {
                                        val walletBalance=it.data.details.walletBalance
                                        val onBoardMerchant=it.data.details.onboarded_merchants
                                        val totalAvailableInventory=it.data.details.total_available_inventory
                                        val totalInventoryRetailer=it.data.details.total_inventory_at_merchant_level
                                        binding.walletBal.text="$$walletBalance"
                                        binding.tvOnboarcdMerchant.text= onBoardMerchant.toString()
                                        binding.tvTotalInventory.text= totalInventoryRetailer.toString()
                                    }

                                    "400" -> {
                                        Const.shortToast(requireContext(), resData.message ?: "")
                                    }

                                    else -> Const.shortToast(requireContext(), resData.message ?: "")
                                }
                            }
                        }
                        Status.LOADING->{

                        }
                        Status.ERROR->{
                            Const.shortToast(requireContext(),it.message.toString())
                        }
                    }
                }
            }

        }
        catch(e:Exception){
            Log.e("abc_wallet", "setUpObserver: ", e)
        }
    }

    private fun setToolUi() {
        binding.toolbar.toolbarMenu.setOnClickListener {
            val parentFragment = parentFragment as? SaleTeamBaseFragment
            parentFragment?.let {
                val drawerLayout = it.binding.drawerLayout
                if (drawerLayout.isDrawerOpen(it.binding.navigationView)) {
                    drawerLayout.closeDrawer(it.binding.navigationView)
                } else {
                    drawerLayout.openDrawer(it.binding.navigationView)
                }
            }
        }
    }




//    private fun setToolUi(){
//        binding.toolbar.toolbarMenu.setOnClickListener {
////            findNavController().popBackStack()
//            (parentFragment as? SaleTeamBaseFragment)?.openDrawer()
//
//        }
//    }

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
