package com.chillarcards.dimasys.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.dimasys.databinding.LayoutMerchantDetailsBinding
import com.chillarcards.dimasys.listener.ItemClickListener
import com.chillarcards.dimasys.modelclass.MerchantDetailsDataClass

class MerchantAdapter(private val itemClickListener: ItemClickListener) : androidx.recyclerview.widget.ListAdapter<MerchantDetailsDataClass,
        MerchantAdapter.ViewHolder>(MerchantDetailsCallBack)
{
    class ViewHolder(private val layoutMerchantDetailsBinding: LayoutMerchantDetailsBinding, private val itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(layoutMerchantDetailsBinding.root)
    {
        fun bind(item: MerchantDetailsDataClass?) {
            with(layoutMerchantDetailsBinding) {
                tvWalletID.text = item?.walletID
                txtValReceiver.text = item?.name
                txtValAmount.text=item?.mid
                txtValDate.text=item?.email
                txtValContact.text=item?.contact
                txtValWalletBalance.text=item?.balance
                txtValReceiveCash.setOnClickListener {
                    itemClickListener.onReceiveCashClick(item?.merchantID.toString())
                }

                txtTransactionHistoryTrans.setOnClickListener {
                    itemClickListener.transactionHistory(item?.merchantID.toString())
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding=LayoutMerchantDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}

object MerchantDetailsCallBack : DiffUtil.ItemCallback<MerchantDetailsDataClass>() {
    override fun areItemsTheSame(
        oldItem: MerchantDetailsDataClass,
        newItem: MerchantDetailsDataClass
    ): Boolean {
        return oldItem.merchantID == newItem.merchantID
    }

    override fun areContentsTheSame(
        oldItem: MerchantDetailsDataClass,
        newItem: MerchantDetailsDataClass
    ): Boolean {
        return oldItem == newItem
    }



}