package com.chillarcards.dimasys.ui.partner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentBaseHomeBinding
import com.chillarcards.dimasys.ui.interfaces.DrawerController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class PartnersBaseFragment : Fragment(), DrawerController {

    private lateinit var binding: FragmentBaseHomeBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val navHostFragment = childFragmentManager
            .findFragmentById(R.id.inner_host_nav) as NavHostFragment
        val navController = navHostFragment.navController

        val navView: NavigationView = findViewById(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.nav_first_fragment -> navController.navigate(R.id.firstFragment)
                R.id.nav_second_fragment -> navController.navigate(R.id.secondFragment)
            }
            true
        }*/



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

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Snackbar.make(binding.drawerLayout, "First item clicked", Snackbar.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    Snackbar.make(binding.drawerLayout, "Second item clicked", Snackbar.LENGTH_SHORT).show()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    override fun openDrawer() {
        Log.d("PartnersBaseFragment", "Opening drawer")
        if (::binding.isInitialized) {
            binding.drawerLayout.openDrawer(binding.navigationView)
        } else {
            Log.e("PartnersBaseFragment", "Binding is not initialized")
            setupDrawer()

        }
    }
}
