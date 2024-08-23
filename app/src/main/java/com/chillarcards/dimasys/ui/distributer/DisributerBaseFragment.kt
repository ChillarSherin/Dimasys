package com.chillarcards.dimasys.ui.distributer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentDistributorBaseBinding
import com.google.android.material.snackbar.Snackbar

class DisributerBaseFragment : Fragment() {

    lateinit var binding: FragmentDistributorBaseBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_distributor_base, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.inner_host_nav) as NavHostFragment
        val navController = navHostFragment.navController

        val headerView = binding.navigationView.getHeaderView(0)
        val textViewHeader: TextView = headerView.findViewById(R.id.tvMerchants)
        textViewHeader.setOnClickListener {
            // Handle the click event
            Snackbar.make(binding.drawerLayout, "Header TextView clicked", Snackbar.LENGTH_SHORT).show()
            // You can perform other actions here, like navigating to a different fragment
        }
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
            // Handle menu item clicks
            true
        }
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(binding.navigationView)
    }
}