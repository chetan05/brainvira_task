package com.example.brainvira_task.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brainvira_task.R
import com.example.brainvira_task.model.RatesResponse
import kotlinx.android.synthetic.main.parent_item.view.*


class RatesItemAdapter(var context: Context
) : RecyclerView.Adapter<RatesItemAdapter.ItemViewHolder>() {

    private var itemList:MutableMap<String,RatesResponse.data> = mutableMapOf()
    private  var childItemAdapter: ChildItemAdapter? = null

    private lateinit var dateArray:ArrayList<String>

    private lateinit var subDataArray: ArrayList<RatesResponse.data>

    private var flag:Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.parent_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun updateItem(
        list: MutableMap<String, RatesResponse.data>,
        keyList: ArrayList<String>,
        dataArray: ArrayList<RatesResponse.data>
    ) {
        itemList = list
        dateArray = keyList
        subDataArray = dataArray
        childItemAdapter?.updateItem(subDataArray)

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(parentViewHolder: ItemViewHolder, position: Int) {
        val context = parentViewHolder.itemView.context

       (parentViewHolder as CartViewHolder).txtItemDateVal.text= dateArray[position]

        with((parentViewHolder).child_recyclerview){
            child_recyclerview.layoutManager = GridLayoutManager(context, 5, RecyclerView.VERTICAL, false)
            childItemAdapter = ChildItemAdapter()
            adapter = childItemAdapter
        }


    }


    open class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class CartViewHolder(view: View) : ItemViewHolder(view) {
        val txtItemDateVal: TextView = view.findViewById(R.id.txtItemDateVal)
        val child_recyclerview: RecyclerView = view.findViewById(R.id.child_recyclerview)
    }
}