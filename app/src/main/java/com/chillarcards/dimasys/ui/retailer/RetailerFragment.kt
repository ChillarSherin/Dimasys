package com.chillarcards.dimasys.ui.retailer

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chillarcards.dimasys.databinding.FragmentHomeBinding
import com.chillarcards.dimasys.databinding.FragmentRetailerBinding
import com.chillarcards.dimasys.utills.CommonDBaseModel
import com.chillarcards.dimasys.ui.interfaces.IAdapterViewUtills
import com.chillarcards.dimasys.ui.partner.PartnersBaseFragment

class RetailerFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentRetailerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRetailerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pInfo =
            activity?.let { activity?.packageManager!!.getPackageInfo(it.packageName, PackageManager.GET_ACTIVITIES) }
        //  val versionName = pInfo?.versionName //Version Name

        setToolUi()

//        binding.bookingViewAll.setOnClickListener {
//            try {
//                findNavController().navigate(
//                    HomeFragmentDirections.actionHomeFragmentToBookingFragment(
//                    )
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }
    private fun setToolUi(){
        binding.toolbar.toolbarMenu.setOnClickListener {
//            findNavController().popBackStack()
            (parentFragment as? RetailerBaseFragment)?.openDrawer()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("abc_mob", "onStop: ")
       // mobileViewModel.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("abc_mob", "onDestroy: ")
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {

    }
}
