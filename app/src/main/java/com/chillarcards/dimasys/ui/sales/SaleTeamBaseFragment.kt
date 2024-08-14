package com.chillarcards.dimasys.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentBaseSalesBinding
import com.chillarcards.dimasys.databinding.FragmentBaseSalesTeamBinding
import com.chillarcards.dimasys.databinding.FragmentBaseSubdistributorBinding

class SaleTeamBaseFragment : Fragment() {

    lateinit var binding: FragmentBaseSalesTeamBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base_sales_team, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.inner_host_nav) as NavHostFragment
        val navController = navHostFragment.navController

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