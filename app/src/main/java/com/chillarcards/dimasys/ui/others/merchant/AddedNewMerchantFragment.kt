package com.chillarcards.dimasys.ui.others.merchant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentAddedNewMerchantBinding


class AddedNewMerchantFragment : Fragment(R.layout.fragment_added_new_merchant) {

lateinit var binding:FragmentAddedNewMerchantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding=FragmentAddedNewMerchantBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackToMerch.setOnClickListener {
            findNavController().navigate(AddedNewMerchantFragmentDirections.actionAddedNewMerchantFragmentToMerchantFragment())
        }
    }


}