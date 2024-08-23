package com.chillarcards.dimasys.ui.login

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.data.viewModel.LoginViewModel
import com.chillarcards.dimasys.databinding.FragmentOtpBinding
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.Const.Companion.maskEmailAddress
import com.chillarcards.dimasys.utills.Const.Companion.maskPhoneNumber
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class OtpFragment : Fragment() {

    lateinit var binding: FragmentOtpBinding
    private val args: OtpFragmentArgs by navArgs()
    private val loginViewModel by viewModel<LoginViewModel>()
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pInfo =
            activity?.let { activity?.packageManager!!.getPackageInfo(it.packageName, PackageManager.GET_ACTIVITIES) }
        val versionName = pInfo?.versionName //Version Name
        prefManager = PrefManager(requireContext())


        binding.version.text = "${getString(R.string.version)}" + Const.ver_title + versionName
        val maskedPhoneNumber = maskPhoneNumber(args.phone.toString())
        val maskedEmailAddress = maskEmailAddress(args.email.toString())
        binding.otpHeadMsg.text="We have send a 6 digit OTP to your registered $maskedPhoneNumber and $maskedEmailAddress"

//        binding.squareField.focusable
//        binding.squareField.addTextChangedListener {
//            val input = it.toString()
//            if (input.isNotEmpty()) {
//                if (input.length==6) {
//                    binding.squareField.error = null
//                    Const.enableButton(binding.otpBtn)
//                }
//
//                else {
//                    //binding.otpView.error = "Are you sure you entered correctly?"
//                    Const.disableButton(binding.otpBtn)
//                }
//            }
//            else {
//                binding.squareField.error = null
//            }
//        }
//
//        binding.otpBtn.setOnClickListener {
//            val otpValue = binding.squareField.text.toString()
//            binding.otpBtn.visibility =View.GONE
//            showProgress()
//            Const.hideSoftKey(requireContext(),view)
//            otpVerify(otpValue)
//        }

        binding.otpView.focusable
        binding.otpView.addTextChangedListener {
            val input = it.toString()
            if (input.isNotEmpty()) {
                if (input.length==6) {
                    binding.otpView.error = null
                    Const.enableButton(binding.otpBtn)

                }
                else {
                    //binding.otpView.error = "Are you sure you entered correctly?"
                    Const.disableButton(binding.otpBtn)
                }
            }
            else {
                binding.otpView.error = null
            }
        }

        binding.otpBtn.setOnClickListener {
            val otpValue = binding.otpView.text.toString()
            binding.otpBtn.visibility =View.GONE
            showProgress()
            Const.hideSoftKey(requireContext(),view)
            otpVerify(otpValue)

        }

    }

    private fun otpVerify(otpValue: String) {
        loginViewModel.clearOtpVerifyData()
        loginViewModel.run {
            userId.value = prefManager.getUserId()
            otp.value = otpValue
            verifyOtp()
        }
        setUpObserver()

    }


    private fun setUpObserver() {
        try {
            loginViewModel.otpVerifyData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { resData ->
                                when (resData.code) {
                                    "200" -> {
                                        prefManager.setIsLoggedIn(true)

                                        when (args.id) {
                                            "Partner" -> {
                                                findNavController().navigate(OtpFragmentDirections.actionMobileFragmentToPartnerBaseFragment()
                                                )
                                            }
                                            "222222" -> {
                                                findNavController().navigate(
                                                    OtpFragmentDirections.actionMobileFragmentToDistributorBaseFragment()
                                                )
                                            }
                                            "Subdistributor"-> {

                                                findNavController().navigate(
                                                    R.id.subDistributorFragment
                                                )
                                            }
                                            "444444" -> {
                                                findNavController().navigate(
                                                    OtpFragmentDirections.actionMobileFragmentToSalesFragment()
                                                )
                                            }
                                            "Salesmember" -> {
                                                findNavController().navigate(R.id.salesTeamsuperFragment)
//                                                findNavController().navigate(
//                                                    OtpFragmentDirections.actionMobileFragmentToSalesTeamsuperFragment()
//                                                )
                                            }
                                            "666666" -> {
                                                findNavController().navigate(
                                                    OtpFragmentDirections.actionMobileFragmentToRetailerFragment()
                                                )
                                            }
                                            else ->{
                                                Const.shortToast(requireContext(),"Not a valid user")
                                                //binding.otpView.cursorColor

                                            }
                                        }

                                    }

                                    "401" -> {
                                        Const.shortToast(requireContext(), resData.message)
                                    }
                                    else -> Const.shortToast(requireContext(), resData.message)
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }
    }

    private fun showProgress() {
        binding.loginProgress.visibility = View.VISIBLE
        binding.otpBtn.visibility = View.INVISIBLE
    }

    private fun hideProgress() {
        binding.loginProgress.visibility = View.GONE
        binding.otpBtn.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        Log.d("abc_mob", "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("abc_mob", "onDestroy: ")
    }

    companion object {
        private const val TAG = "OTPFragment"
    }




}