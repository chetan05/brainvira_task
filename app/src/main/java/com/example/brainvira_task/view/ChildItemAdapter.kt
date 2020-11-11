package com.example.brainvira_task.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brainvira_task.R
import com.example.brainvira_task.model.RatesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ChildItemAdapter() : RecyclerView.Adapter<ChildItemAdapter.ItemViewHolder>()  {


    private lateinit var itemList:ArrayList<RatesResponse.data>

    private var repeat = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.child_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun updateItem(list: ArrayList<RatesResponse.data>) {
        itemList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val context = holder.itemView.context
        val childDataAsMap: Map<String, Any> = itemList[position].serializeToMap()
        bindCartItemView(context ,childDataAsMap ,holder as CartViewHolder)

    }

    private fun bindCartItemView(
        context: Context,
        childDataAsMap: Map<String, Any>,
        holder: CartViewHolder
    ) {
        childDataAsMap.forEach foreach@{ _childDataAsMap ->
            holder.apply {

               // txtName.text = _childDataAsMap.key
              //  txtData.text = _childDataAsMap.value.toString()
               // holder.itemView.setHasTransientState(true)

            }

        }
     //   holder.itemView.setHasTransientState(false)

        for ((key,value) in childDataAsMap){
            holder.apply {
                txtName1.text = key
                txtData1.text = value.toString()

            }
        }

    }

    val gson = Gson()

    //convert a data class to a map
    fun <T> T.serializeToMap(): Map<String, Any> {
        return convert()
    }

    //convert a map to a data class
    inline fun <reified T> Map<String, Any>.toDataClass(): T {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }

    open class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class CartViewHolder(view: View) : ItemViewHolder(view) {
        val txtName1: TextView = view.findViewById(R.id.txtName1)
        val txtData1: TextView = view.findViewById(R.id.txtData1)
    }
}