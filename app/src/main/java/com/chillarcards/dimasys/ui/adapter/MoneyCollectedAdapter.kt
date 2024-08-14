package com.chillarcards.dimasys.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.dimasys.data.model.total_billing.res.Details
import com.chillarcards.dimasys.databinding.LayoutMoneyCollectedBinding
import com.chillarcards.dimasys.databinding.LayoutTotalBillingBinding


class MoneyCollectedAdapter(
) : ListAdapter<com.chillarcards.dimasys.data.model.money_collected.res.Details, MoneyCollectedAdapter.ViewHolder>(MoneyCollectedCallback) {
    class ViewHolder(private val layoutMoneyCollectedBinding: LayoutMoneyCollectedBinding) : RecyclerView.ViewHolder(layoutMoneyCollectedBinding.root) {
        fun bind(item: com.chillarcards.dimasys.data.model.money_collected.res.Details?) {
            with(layoutMoneyCollectedBinding){
                txtCards.text = item?.cards
                txtBank.text = item?.oxxo
                txtOffline.text = item?.offline
                txtRetailers.text = item?.transfer
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = LayoutMoneyCollectedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

object MoneyCollectedCallback : DiffUtil.ItemCallback<com.chillarcards.dimasys.data.model.money_collected.res.Details>() {
    override fun areItemsTheSame(oldItem: com.chillarcards.dimasys.data.model.money_collected.res.Details, newItem: com.chillarcards.dimasys.data.model.money_collected.res.Details): Boolean {
        return oldItem.cards == newItem.cards
    }

    override fun areContentsTheSame(oldItem: com.chillarcards.dimasys.data.model.money_collected.res.Details, newItem: com.chillarcards.dimasys.data.model.money_collected.res.Details): Boolean {
        return oldItem == newItem
    }
}
