package com.chillarcards.dimasys.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.dimasys.R
import com.chillarcards.dimasys.utills.CommonDBaseModel
import com.chillarcards.dimasys.ui.Dummy
import com.chillarcards.dimasys.ui.interfaces.IAdapterViewUtills

class TransactionAdapter(private val items: List<Dummy>,
                         context: Context?,
                         private val getAdapterUtil: IAdapterViewUtills)
    : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.BookingView.setOnClickListener {
            val commonDObj = CommonDBaseModel()
            commonDObj.mastIDs = item.id.toString()
            commonDObj.itmName = item.name
            commonDObj.valueStr1 = item.custname
            val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
            sCommonDAry.add(commonDObj)
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "VIEW")
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val BookingView: CardView = itemView.findViewById(R.id.book_frm)
        private val tranDateTextView: TextView = itemView.findViewById(R.id.tran_date)
        val CustomNameTextView: TextView = itemView.findViewById(R.id.tran_cust_name)
        val StaffNameTextView: TextView = itemView.findViewById(R.id.tran_sales_name)
        val StaffSerTextView: TextView = itemView.findViewById(R.id.tran_sales_ser)
        val CustomerCallTextView: ImageView = itemView.findViewById(R.id.tran_call)

        fun bind(item: Dummy) {
            tranDateTextView.text = item.name
            CustomNameTextView.text = item.custname
//            itemView.idTextView.text = "ID: ${item.id}"
//            itemView.imageView.setImageResource(item.imageResId)
        }

    }

    fun getFirstLetterAfterSpace(inputText: String): String {
        val words = inputText.split(" ")
        val result = StringBuilder()

        for (word in words) {
            if (word.isNotEmpty()) {
                val firstChar = word[0]
                result.append(firstChar)
            }
        }

        return result.toString()
    }

}
