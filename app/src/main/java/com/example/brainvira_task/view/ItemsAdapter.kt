package com.example.brainvira_task.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainvira_task.R
import com.example.brainvira_task.model.SearchItemDetailsResponse
import com.example.brainvira_task.model.SearchResultResponse

class ItemsAdapter(
    var context: Context
) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var itemList: MutableList<SearchResultResponse.SearchQuiryResult> = mutableListOf()
   private var searchItemDetailsResponse :SearchItemDetailsResponse? = null

    var onItemClick: ((SearchItemDetailsResponse) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_carousel,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun updateItem(list: MutableList<SearchResultResponse.SearchQuiryResult>) {
        itemList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val context = holder.itemView.context
        val orderItemData = itemList[position]
        bindCartItemView(context, orderItemData, holder as CartViewHolder)

    }
    private fun bindCartItemView(
        context: Context,
        itemData: SearchResultResponse.SearchQuiryResult,
        holder: CartViewHolder
    ) {
        holder.apply {
            txtPItemName.text = itemData.title
            for (imageURL in itemData.images) {
                imgItem.tag = imageURL.link
                Glide.with(context).load(imageURL.link).into(imgItem)
            }
            imgItem.setOnClickListener {
                searchItemDetailsResponse = SearchItemDetailsResponse(itemData.id,itemData.title,imgItem.tag.toString())
                searchItemDetailsResponse?.let { it1 -> onItemClick?.invoke(it1) }
            }
        }
    }

    open class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class CartViewHolder(view: View) : ItemViewHolder(view) {
        val imgItem: ImageView = view.findViewById(R.id.ivItem)
        val txtPItemName: TextView = view.findViewById(R.id.txtItemName)
    }
}