package com.chillarcards.dimasys.ui.others.total_payment_collected

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.databinding.FragmentTotalPaymentCollectedBinding
import com.chillarcards.dimasys.ui.subdistributer.SubDistributerViewModel
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class TotalPaymentCollectedFragment : Fragment() {

    private val totalPaymentCollectedViewModel by viewModel<TotalPaymentCollectedViewModel>()
    lateinit var prefManager: PrefManager
    lateinit var binding: FragmentTotalPaymentCollectedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTotalPaymentCollectedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager= PrefManager(requireContext())
        binding.myToolbar.setOnClickListener {
            findNavController().navigateUp()
        }
        totalPaymentCollectedViewModel.run {
            userID.value=prefManager.getUserId()
            totalPaymentCollectedViewModel.getMerchantBalance()
        }
        setUpObserver()
    }

    private fun setUpObserver() {
        try {
            totalPaymentCollectedViewModel.merchantBal.observe(viewLifecycleOwner){
                if (it!=null){
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let {resData ->
                                when(resData.code){
                                    "200"->{
                                        val merchantBallance=it.data.details.merchant
                                        binding.tvMerchantBal.text=merchantBallance
                                    }
                                    "400"->{
                                        Const.shortToast(requireContext(),resData.message)
                                    }

                                    else-> Const.shortToast(requireContext(),resData.message)

                                }
                            }
                        }

                        Status.LOADING->{

                        }
                        Status.ERROR->
                            {Const.shortToast(requireContext(),it.message.toString())}
                    }
                }
            }

        }
        catch (e:Exception){
            Log.e("abc_wallet", "setUpObserver: ", e)
        }
    }

}