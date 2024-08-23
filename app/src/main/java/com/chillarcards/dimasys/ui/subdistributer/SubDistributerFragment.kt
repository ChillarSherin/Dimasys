package com.chillarcards.dimasys.ui.subdistributer

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.MainActivity
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentSubdistributorBinding
import com.chillarcards.dimasys.utills.CommonDBaseModel
import com.chillarcards.dimasys.ui.interfaces.IAdapterViewUtills
import com.chillarcards.dimasys.ui.partner.PartnersBaseFragment
import com.chillarcards.dimasys.ui.partner.PartnersFragmentDirections
import com.chillarcards.dimasys.ui.sales.SaleTeamBaseFragment
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubDistributerFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentSubdistributorBinding
    private val subDistributerViewModel by viewModel<SubDistributerViewModel>()
    private lateinit var prefManager: PrefManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubdistributorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        val pInfo =
            activity?.let { activity?.packageManager!!.getPackageInfo(it.packageName, PackageManager.GET_ACTIVITIES) }
        //  val versionName = pInfo?.versionName //Version Name
        setToolUi()
        binding.applyLoan.setOnClickListener {
            showAlertDialogButtonClicked()
        }

      //  languageSelector()







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

        septUpclick()
      subDistributerViewModel.run {
          userID.value=prefManager.getUserId()
          subDistributerViewModel.getWalletBalance()
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
            getTotalBill()
        }

        binding.moneyCollect.setOnClickListener {
            getMoneyCollect()
        }
    }

    private fun getRecentProducts() {
        findNavController().navigate(SubDistributerFragmentDirections.actionSubDistributorFragmentToRecentProductsFragment())
    }

    private fun getRecentTransactions(){
      findNavController().navigate(R.id.recentTransactionsFragment)
    }

    private fun getTotalBill(){
        findNavController().navigate(R.id.totalBillingFragment)
       // findNavController().navigate(SubDistributerFragmentDirections.actionSubDistributorFragmentToTotalBillingFragment())
    }
    private fun getMoneyCollect(){
        findNavController().navigate(R.id.moneyCollectedFragment2)
    }

    private fun setUpObserver(){
        try {
            subDistributerViewModel.walletBalance.observe(viewLifecycleOwner){
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

                                        binding.walletBal.text = "$$walletBalance"
                                        binding.tvOnBoardMerchant.text= onBoardMerchant.toString()
                                        binding.tvAvailableInventory.text= totalAvailableInventory.toString()
                                        binding.tvRetailLevelInventory.text= totalInventoryRetailer.toString()
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
            val parentFragment = parentFragment as? SubDisributerBaseFragment
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
//            (parentFragment as? SubDisributerBaseFragment)?.openDrawer()
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
    fun showAlertDialogButtonClicked() {
        val builder = AlertDialog.Builder(requireContext())
     //   builder.setTitle("Name")

        // set the custom layout
        val customLayout: View = layoutInflater.inflate(R.layout.custom_layout_loan, null)
        builder.setView(customLayout)

        // add a button
//        builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
//            // send data from the AlertDialog to the Activity
////            val editText = customLayout.findViewById<EditText>(R.id.editText)
////            sendDialogDataToActivity(editText.text.toString())
//        }
        // create and show the alert dialog
        val dialog = builder.create()
        val comingSoon=customLayout.findViewById<ImageView>(R.id.ivImageView)
        comingSoon?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }
    private fun languageSelector(){
        val languages = resources.getStringArray(R.array.Languages)
        val spinner = binding.toolbar.spinner

// Set up the adapter for the Spinner
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            languages
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                (view as TextView).setTextColor(Color.BLACK)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                (view as TextView).setTextColor(Color.BLACK)
                return view
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

// Handle item selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Show dialog only when an item is selected
                val message = when (position) {
                    0 -> "Would you like to choose English as your primary language?"
                    1 -> "Would you like to choose Spanish as your primary language?"
                    else -> ""
                }

                if (message.isNotEmpty()) {
                    val mBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Confirm")
                        .setMessage(message)
                        .setPositiveButton("Yes") { dialog, _ ->
                            // Handle the positive button action
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()

                    // Set the button colors after the dialog is shown
                    mBuilder.setOnShowListener {
                        mBuilder.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                        mBuilder.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                    }

                    mBuilder.show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: handle the case where no item is selected
            }
        }
    }

}
