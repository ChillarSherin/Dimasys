package com.chillarcards.dimasys.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.dimasys.databinding.LayoutTransactionHistoryBinding
import com.chillarcards.dimasys.modelclass.CashCollectionModel

class TransactionHistoryAdapter():ListAdapter<CashCollectionModel,TransactionHistoryAdapter.ViewHolder>(TransactionHistoryCallBack)

{
    class ViewHolder(private val layoutTransactionHistoryBinding: LayoutTransactionHistoryBinding) : RecyclerView.ViewHolder(layoutTransactionHistoryBinding.root)
    {
        fun bind(item: CashCollectionModel?) {
            with(layoutTransactionHistoryBinding) {
                txtValReceiver.text = item?.amount
                txtValAmount.text=item?.transactionUID
                txtValDate.text=item?.transactionCreatedON

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=LayoutTransactionHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object TransactionHistoryCallBack: DiffUtil.ItemCallback<CashCollectionModel>() {
        override fun areItemsTheSame(
            oldItem: CashCollectionModel,
            newItem: CashCollectionModel
        ): Boolean {
           return oldItem.transactionUID==newItem.transactionUID
        }

        override fun areContentsTheSame(
            oldItem: CashCollectionModel,
            newItem: CashCollectionModel
        ): Boolean {
            return oldItem==newItem
        }
    }
}