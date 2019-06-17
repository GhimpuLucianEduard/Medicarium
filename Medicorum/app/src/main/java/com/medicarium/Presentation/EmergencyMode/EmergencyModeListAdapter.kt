package com.medicarium.Presentation.EmergencyMode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.medicarium.R
import kotlinx.android.synthetic.main.emergency_mode_card.view.*

class EmergencyModeListAdapter(
    private var data: List<EmergencyModeEntry>
) : RecyclerView.Adapter<EmergencyModeListAdapter.EmergencyModeViewHolder>() {

    fun setItems(entries: List<EmergencyModeEntry>) {
        data = entries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyModeViewHolder {
        return EmergencyModeViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.emergency_mode_card, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: EmergencyModeViewHolder, position: Int) {
        val item = data[position]
        holder.nameTextView.text = item.name
        holder.value.text = item.value
    }

    inner class EmergencyModeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.propertyNameTextView
        val value: TextView = view.propertyValueTextView
    }
}