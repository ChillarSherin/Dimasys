package com.chillarcards.dimasys.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransaction
import com.chillarcards.dimasys.databinding.LayoutTransactionsHolderBinding
import com.chillarcards.dimasys.utills.CommonDBaseModel
import com.chillarcards.dimasys.ui.Dummy
import com.chillarcards.dimasys.ui.interfaces.IAdapterViewUtills

class RecentTransactionAdapter(
) : ListAdapter<RecentTransaction,RecentTransactionAdapter.ViewHolder>(RecentTransactionsCallback) {
    class ViewHolder(private val layoutTransactionsHolderBinding: LayoutTransactionsHolderBinding) : RecyclerView.ViewHolder(layoutTransactionsHolderBinding.root) {
        fun bind(item: RecentTransaction?) {
            with(layoutTransactionsHolderBinding){
                txtValAmount.text = item?.amount
                txtValSender.text = item?.sender
                txtValReceiver.text = item?.receiver
                txtValDate.text = item?.date
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = LayoutTransactionsHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

object RecentTransactionsCallback : DiffUtil.ItemCallback<RecentTransaction>() {
    override fun areItemsTheSame(oldItem: RecentTransaction, newItem: RecentTransaction): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: RecentTransaction, newItem: RecentTransaction): Boolean {
        return oldItem == newItem
    }
}
