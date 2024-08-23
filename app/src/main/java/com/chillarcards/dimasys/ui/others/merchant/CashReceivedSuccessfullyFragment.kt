package com.chillarcards.dimasys.ui.others.merchant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentCashReceiveBinding
import com.chillarcards.dimasys.databinding.FragmentCashReceivedSuccessfullyBinding
import com.chillarcards.dimasys.ui.login.OtpFragmentArgs
import com.chillarcards.dimasys.utills.PrefManager


class CashReceivedSuccessfullyFragment : Fragment(R.layout.fragment_cash_received_successfully) {

lateinit var binding:FragmentCashReceivedSuccessfullyBinding
    private val args: CashReceivedSuccessfullyFragmentArgs by navArgs()
    private lateinit var prefManager: PrefManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCashReceivedSuccessfullyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager= PrefManager(requireContext())
        val userType = prefManager.getUserType()
        Log.d("CashReceivedSuccessfullyFragment", PrefManager(requireContext()).getUserType())

        binding.tvBackToHome.setOnClickListener {
            userTypeFragmentNavigation(userType)
           // findNavController().navigate(CashReceivedSuccessfullyFragmentDirections.actionCashReceivedSuccessfullyFragmentToSubDistributorFragment())
        }
    }

 fun userTypeFragmentNavigation(userType: String){
    when(userType){
        "Salesmember"->{
            Log.d("CashReceivedSuccessfullyFragment", "Navigating to SalesTeamManagerFragment")
            findNavController().navigate(R.id.salesTeamsuperFragment)
        }
        "Subdistributor"->{
            Log.d("CashReceivedSuccessfullyFragment", "Navigating to SubDistributorFragment")
            findNavController().navigate(CashReceivedSuccessfullyFragmentDirections.actionCashReceivedSuccessfullyFragmentToSubDistributorFragment())
        }

        else->{
            Toast.makeText(requireContext(),"not valid",Toast.LENGTH_SHORT).show()
            Log.d("CashReceivedSuccessfullyFragment", "Unexpected id: ${args.id}")
        }
    }
}


}