package com.chillarcards.dimasys.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.data.model.partner_products.res.RecentProduct
import com.chillarcards.dimasys.data.model.total_billing.res.Details
import com.chillarcards.dimasys.databinding.FragmentTotalBillingBinding
import com.chillarcards.dimasys.databinding.LayoutProductsHolderBinding
import com.chillarcards.dimasys.databinding.LayoutTotalBillingBinding


class BillingAdapter(
) : ListAdapter<Details, BillingAdapter.ViewHolder>(BillingCallback) {
    class ViewHolder(private val layoutTotalBillingBinding: LayoutTotalBillingBinding) : RecyclerView.ViewHolder(layoutTotalBillingBinding.root) {
        fun bind(item: Details?) {
            with(layoutTotalBillingBinding){
                txtUntilDate.text = item?.untilDate
                txtCurrentMonth.text = item?.currentMonth
                txtPrevMonth.text = item?.previousMonth
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = LayoutTotalBillingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

object BillingCallback : DiffUtil.ItemCallback<Details>() {
    override fun areItemsTheSame(oldItem: Details, newItem: Details): Boolean {
        return oldItem.untilDate == newItem.untilDate
    }

    override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean {
        return oldItem == newItem
    }
}
