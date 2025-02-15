package com.mpg.mycallhistory.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mpg.mycallhistory.R

class RvAdapter : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    private var items: List<ListItem> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.contactName)
        val contactNumberTextView: TextView = itemView.findViewById(R.id.contactPhoneNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.contactNumberTextView.text = item.phoneNumber
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(newItems: List<ListItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
