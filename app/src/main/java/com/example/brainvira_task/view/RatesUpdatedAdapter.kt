package com.example.brainvira_task.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.brainvira_task.R
import com.example.brainvira_task.Util.StickyRecyclerListener
import com.example.brainvira_task.Util.UIUtils
import com.example.brainvira_task.model.CustomerOrderDataList
import kotlinx.android.synthetic.main.child_sub_item.view.*
import kotlinx.android.synthetic.main.parent_item.view.*

class RatesUpdatedAdapter(val context: Context) :
    ListAdapter<CustomerOrderDataList, RecyclerView.ViewHolder>(MODEL_DIFF_UTIL_CALLBACK),
    StickyRecyclerListener {

    @NonNull
    override fun onCreateViewHolder(
        @NonNull viewGroup: ViewGroup, viewType: Int
    )
            : RecyclerView.ViewHolder {
        val itemView: View
        return if (viewType == ItemType.TYPE_DATE_HEADER) {
            itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.parent_item, viewGroup, false)
            HeaderViewHolder(itemView)
        } else {
            itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.child_sub_item, viewGroup, false)
            RatesViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(@NonNull viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (ItemType.TYPE_DATE_HEADER) {
            getItemViewType(position) -> {
                (viewHolder as HeaderViewHolder).bind(getItem(position))
            }
            else -> {
                (viewHolder as RatesViewHolder).bind(getItem(position))
            }
        }
    }

    internal inner class HeaderViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val monthTextViewHeader = itemView.txtItemDateVal

        // private val bottomDivider = itemView.bottomDivider
        fun bind(model: CustomerOrderDataList) {
            monthTextViewHeader.text = model.date
            //  bottomDivider.GONE()
        }
    }

    internal inner class RatesViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(rates: CustomerOrderDataList) {
            itemView.apply {
                txtData1.text = rates.item?.AUD.toString()
                txtData2.text = rates.item?.BGN.toString()
                txtData3.text = rates.item?.BRL.toString()
                txtData4.text = rates.item?.CAD.toString()
                txtData5.text = rates.item?.CHF.toString()
                txtData6.text = rates.item?.CNY.toString()
                txtData7.text = rates.item?.CZK.toString()
                txtData8.text = rates.item?.DKK.toString()
                txtData9.text = rates.item?.EUR.toString()
                txtData10.text = rates.item?.GBP.toString()
                txtData11.text = rates.item?.HKD.toString()
                txtData12.text = rates.item?.HRK.toString()
                txtData13.text = rates.item?.HUF.toString()
                txtData14.text = rates.item?.IDR.toString()
                txtData15.text = rates.item?.ILS.toString()
                txtData16.text = rates.item?.INR.toString()
                txtData17.text = rates.item?.ISK.toString()
                txtData18.text = rates.item?.JPY.toString()
                txtData19.text = rates.item?.KRW.toString()
                txtData20.text = rates.item?.MXN.toString()
                txtData21.text = rates.item?.MYR.toString()
                txtData22.text = rates.item?.NOK.toString()
                txtData23.text = rates.item?.NZD.toString()
                txtData24.text = rates.item?.PHP.toString()
                txtData25.text = rates.item?.PLN.toString()
                txtData26.text = rates.item?.RON.toString()
                txtData27.text = rates.item?.RUB.toString()
                txtData28.text = rates.item?.SEK.toString()
                txtData29.text = rates.item?.SGD.toString()
                txtData30.text = rates.item?.THB.toString()
                txtData31.text = rates.item?.TRY.toString()
                txtData32.text = rates.item?.USD.toString()
                txtData33.text = rates.item?.ZAR.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type

    override fun getHeaderPositionForItem(itemPosition: Int?): Int? {
        var headerPosition: Int? = 0
        itemPosition?.downTo(1)?.forEach { i ->
            if (isHeader(i) == true) {
                headerPosition = i
                return headerPosition
            }
        }
        return headerPosition
    }

    override fun getHeaderLayout(headerPosition: Int?): Int? = R.layout.parent_item
    override fun bindHeaderData(header: View, headerPosition: Int?) {
        val dateTextViewHeader = header.findViewById(R.id.txtItemDateVal) as TextView
        dateTextViewHeader.text =
            headerPosition?.let {
                UIUtils.getDate(
                    getItem(it).date,
                    UIUtils.PICKUP_FORMAT_HISTORY_DATE
                )
            }
    }

    override fun isHeader(itemPosition: Int?): Boolean? =
        itemPosition?.let { getItem(it).type == ItemType.TYPE_DATE_HEADER }

    companion object {
        val MODEL_DIFF_UTIL_CALLBACK: DiffUtil.ItemCallback<CustomerOrderDataList> =
            object : DiffUtil.ItemCallback<CustomerOrderDataList>() {
                override fun areItemsTheSame(
                    @NonNull model1: CustomerOrderDataList,
                    @NonNull model2: CustomerOrderDataList
                ): Boolean {
                    return model1.date == model2.date
                }

                override fun areContentsTheSame(
                    @NonNull model1: CustomerOrderDataList,
                    @NonNull model2: CustomerOrderDataList
                ): Boolean {
                    return model1.date == model2.date
                }
            }
    }

    object ItemType {
        const val TYPE_RATES = 1
        const val TYPE_DATE_HEADER = 2
    }
}