package com.chillarcards.dimasys.ui.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.databinding.FragmentMoneyCollectedBinding
import com.chillarcards.dimasys.databinding.FragmentTotalPaymentCollectedBinding


class TotalPaymentCollectedFragment : Fragment() {


    lateinit var binding: FragmentTotalPaymentCollectedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTotalPaymentCollectedBinding.inflate(layoutInflater)
        return binding.root
    }

}