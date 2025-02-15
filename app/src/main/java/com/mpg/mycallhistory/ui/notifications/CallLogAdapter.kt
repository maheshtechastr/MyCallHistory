package com.mpg.mycallhistory.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mpg.mycallhistory.R
import java.util.Date

class CallLogAdapter : RecyclerView.Adapter<CallLogAdapter.ViewHolder>() {

    private var items: List<CallLogItem> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberTextView: TextView = itemView.findViewById(R.id.textViewNumber)
        val typeTextView: TextView = itemView.findViewById(R.id.textViewType)
        val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)
        val durationTextView: TextView = itemView.findViewById(R.id.textViewDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_call_log, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.numberTextView.text = item.number
        holder.typeTextView.text = item.type.toString()
        holder.dateTextView.text = Date(item.date).toString()
        holder.durationTextView.text = item.duration.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(newItems: List<CallLogItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
