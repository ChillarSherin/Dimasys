package com.chillarcards.dimasys.ui.login

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.data.viewModel.LoginViewModel
import com.chillarcards.dimasys.databinding.FragmentMobileBinding
import com.chillarcards.dimasys.utills.Const
import com.chillarcards.dimasys.utills.PrefManager
import com.chillarcards.dimasys.utills.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class MobileFragment : Fragment() {

    lateinit var binding: FragmentMobileBinding
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMobileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pInfo =
            activity?.let {
                activity?.packageManager!!.getPackageInfo(
                    it.packageName,
                    PackageManager.GET_ACTIVITIES
                )
            }
        val versionName = pInfo?.versionName //Version Name


        binding.version.text = "${getString(R.string.version)}" + Const.ver_title + versionName

        binding.usernameEt.addTextChangedListener {
            val input = it.toString()
            if (input.isNotEmpty()) {
                if (!input.matches(Const.emailRegex)) {
                    binding.username.error = "Are you sure you entered correctly?"
                    Const.disableButton(binding.loginBtn)
                } else {
                    binding.username.error = null
                    binding.username.isErrorEnabled = false
                    Const.enableButton(binding.loginBtn)
                }
            } else {
                binding.username.error = null
                binding.username.isErrorEnabled = false
            }
        }
        binding.loginBtn.setOnClickListener {

            val username = binding.usernameEt.text.toString()
            val password = binding.passwordEt.text?.trim().toString()
            when {
                !Const.emailRegex.containsMatchIn(username) -> {
                    binding.username.error = getString(R.string.username)
                }

                else -> {
                    binding.loginBtn.visibility = View.GONE
                    showProgress()
                    mobileVerify(username, password, view)
                }
            }

        }

        setTextColorForTerms()

    }

    private fun mobileVerify(username: String, pass: String, view: View) {
        loginViewModel.clearOtpVerifyData()
        loginViewModel.run {
            email.value = username
            password.value = pass
            setlogin()
        }
        setUpObserver(view)

    }

    private fun setUpObserver(view: View) {
        try {
            loginViewModel.loginData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { resData ->
                                when (resData.code) {
                                    "200" -> {
                                        PrefManager(requireContext()).setUserId(resData.details.userID)
                                        PrefManager(requireContext()).setUserType(resData.details.userType)
                                        Log.d("LoginSuccess", "UserID: ${resData.details.userID}, UserType: ${resData.details.userType}, UserPhone: ${resData.details.userPhone}")
                                        try {
                                            findNavController().navigate(
                                                MobileFragmentDirections.actionMobileFragmentToOtpFragment(
                                                    resData.details.userType,
                                                    binding.usernameEt.text.toString(),
                                                    resData.details.userPhone
                                                )
                                            )
                                        } catch (e: IllegalArgumentException) {
                                            Log.e("NavigationError", "Navigation action failed", e)

                                        }

                                    }

                                    "400" -> {
                                        Const.shortToast(requireContext(), resData.message)
                                        binding.usernameEt.setText("")
                                        binding.passwordEt.setText("")
                                        binding.username.requestFocus();
                                        Const.hideSoftKey(requireContext(), view)
                                    }

                                    else -> Const.shortToast(requireContext(), resData.message)
                                }

                            }?:run {
                                Log.e("LoginError", "Response data is null.")
                                Const.shortToast(requireContext(), "Error: Response data is null.")
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
        binding.loginBtn.visibility = View.INVISIBLE
    }

    private fun hideProgress() {
        binding.loginProgress.visibility = View.GONE
        binding.loginBtn.visibility = View.VISIBLE
    }

    private fun setTextColorForTerms() {
        try {
            val text = "Terms & conditions and Privacy Policy"

            val wordToSpan: Spannable = SpannableString(text)

            //TODO
            //need to enable before release API pending

            // Clickable span for "Terms & conditions"
            val termsClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
//                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chillarpayments.com/terms-and-conditions.html"))
//                    startActivity(browserIntent)
                }
            }

            // Clickable span for "Privacy Policy"
            val privacyPolicyClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
//                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chillarpayments.com/aboutus.html"))
//                    startActivity(browserIntent)
                }
            }

            // Set clickable spans for "Terms & conditions"
            wordToSpan.setSpan(
                termsClickableSpan,
                text.indexOf("Terms & conditions"),
                text.indexOf("Terms & conditions") + "Terms & conditions".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Set clickable spans for "Privacy Policy"
            wordToSpan.setSpan(
                privacyPolicyClickableSpan,
                text.indexOf("Privacy Policy"),
                text.indexOf("Privacy Policy") + "Privacy Policy".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Make "Terms & conditions" and "Privacy Policy" bold
            wordToSpan.setSpan(
                StyleSpan(Typeface.BOLD),
                text.indexOf("Terms & conditions"),
                text.indexOf("Terms & conditions") + "Terms & conditions".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            wordToSpan.setSpan(
                StyleSpan(Typeface.BOLD),
                text.indexOf("Privacy Policy"),
                text.indexOf("Privacy Policy") + "Privacy Policy".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Set color for both links
            wordToSpan.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)),
                text.indexOf("Terms & conditions"),
                text.indexOf("Terms & conditions") + "Terms & conditions".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            wordToSpan.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)),
                text.indexOf("Privacy Policy"),
                text.indexOf("Privacy Policy") + "Privacy Policy".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Set the text and movement method
            binding.terms1.text = wordToSpan
            binding.terms1.movementMethod = LinkMovementMethod.getInstance()
        } catch (e: Exception) {
            Log.e("abc_mobile", "setTextColorForTerms: msg: ", e)
        }
    }

    fun onLoadSMS() {
        // on the below line we are creating a try and catch block
        try {

            val message =
                "858585 is your verification OTP for accessing the BHC. Do not share this OTP or your number with anyone.yaMqX9A+vNH"
            val uri: Uri = Uri.parse("smsto:+919744496378")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", message)
            startActivity(intent)

        } catch (e: Exception) {
            // on catch block we are displaying toast message for error.
        }
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
        private const val TAG = "MobileFragment"
    }
}