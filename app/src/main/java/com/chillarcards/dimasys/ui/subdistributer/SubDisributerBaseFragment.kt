package com.chillarcards.dimasys.ui.subdistributer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.MainActivity
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentBaseSubdistributorBinding
import com.chillarcards.dimasys.utills.PrefManager
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class SubDisributerBaseFragment : Fragment(){
    private var doubleBackToExitPressedOnce = false
    lateinit var binding: FragmentBaseSubdistributorBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var  prefManager:PrefManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base_subdistributor, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       prefManager= PrefManager(requireContext())
        val navHostFragment = childFragmentManager.findFragmentById(R.id.inner_host_nav) as NavHostFragment
        val navController = navHostFragment.navController
        val headerView = binding.navigationView.getHeaderView(0)
        val textViewHeader: TextView = headerView.findViewById(R.id.tvMerchants)
      requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(true){
    override fun handleOnBackPressed() {
        if (doubleBackToExitPressedOnce){
            requireActivity().finish()
        }
        else{
            doubleBackToExitPressedOnce=true
            Toast.makeText(requireContext(), "if press back again you will be exited from Application", Toast.LENGTH_SHORT).show()
            // Reset the flag after a delay
            view.postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

})
        textViewHeader.setOnClickListener {
            navController.navigate(SubDistributerFragmentDirections.actionSubDistributorFragmentToMerchantFragment())

            binding.drawerLayout.closeDrawers()
//            findNavController().navigate(SubDistributerFragmentDirections.actionSubDistributorFragmentToMerchantFragment())
//            binding.drawerLayout.closeDrawers()
         //   Snackbar.make(binding.drawerLayout, "Header TextView clicked", Snackbar.LENGTH_SHORT).show()

        }
        binding.logout.setOnClickListener {
            val mBuilder = AlertDialog.Builder(requireContext())
                .setTitle("Confirm")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes") { dialog, _ ->
                    prefManager.setIsLoggedIn(false)
                    prefManager.clearAll()
                    prefManager.clearUserId()
                    // Navigate to MainActivity and clear the back stack
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                    activity?.finishAffinity() // Closes all activities in the task and removes them from the back stack
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            // Set the button colors after the dialog is shown
            mBuilder.setOnShowListener {
                mBuilder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                mBuilder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            }

            mBuilder.show()
        }


//        binding.logout.setOnClickListener {
//            prefManager.setIsLoggedIn(false)
//           prefManager.clearAll()
//            prefManager.clearUserId()
//            val mBuilder = AlertDialog.Builder(requireContext())
//                .setTitle("Confirm")
//                .setMessage("Are you sure you want to exit?")
//                .setPositiveButton("Yes", null)
//                .setNegativeButton("No", null)
//                .show()
//            val mPositiveButton = mBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
//            mPositiveButton.setOnClickListener {
//                exitProcess(0)
//            }
//            val intent = Intent(requireContext(), MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            startActivity(intent)
//            activity?.finish()
//
//        }
        setupDrawer()
     }

    private fun setupDrawer() {
        drawerToggle = ActionBarDrawerToggle(
            activity,
            binding.drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Optional: Handle navigation view item clicks here
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
//            menuItem.isChecked=true
//            when(menuItem.itemId) {
//                R.id.nav_logout -> {
//
//                }
//            }

            // Handle menu item clicks
            true
        }
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(binding.navigationView)
    }

}