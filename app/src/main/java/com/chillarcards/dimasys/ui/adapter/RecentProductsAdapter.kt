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
import com.chillarcards.dimasys.data.model.transaction.res.RecentTransaction
import com.chillarcards.dimasys.databinding.FragmentRecentProductsBinding
import com.chillarcards.dimasys.databinding.LayoutProductsHolderBinding
import com.chillarcards.dimasys.databinding.LayoutTransactionsHolderBinding
import com.chillarcards.dimasys.utills.CommonDBaseModel
import com.chillarcards.dimasys.ui.Dummy
import com.chillarcards.dimasys.ui.interfaces.IAdapterViewUtills

class RecentProductsAdapter(
) : ListAdapter<RecentProduct, RecentProductsAdapter.ViewHolder>(RecentProductsCallback) {
    class ViewHolder(private val layoutProductsHolderBinding: LayoutProductsHolderBinding) : RecyclerView.ViewHolder(layoutProductsHolderBinding.root) {
        fun bind(item: RecentProduct?) {
            with(layoutProductsHolderBinding){
                txtProdCode.text = item?.productCode
                txtProdName.text = item?.productName
                txtAvailableQuantity.text = item?.availableQuantity
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = LayoutProductsHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

object RecentProductsCallback : DiffUtil.ItemCallback<RecentProduct>() {
    override fun areItemsTheSame(oldItem: RecentProduct, newItem: RecentProduct): Boolean {
        return oldItem.productID == newItem.productID
    }

    override fun areContentsTheSame(oldItem: RecentProduct, newItem: RecentProduct): Boolean {
        return oldItem == newItem
    }
}
