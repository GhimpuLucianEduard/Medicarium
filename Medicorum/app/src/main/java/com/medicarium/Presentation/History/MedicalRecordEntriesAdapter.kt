package com.medicarium.Presentation.History

import android.content.Context
import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.medicarium.Data.Models.MedicalRecordEntry
import com.medicarium.Presentation.History.MedicalRecordEntriesAdapter.ViewHolder
import empty
import kotlinx.android.synthetic.main.medical_record_entry_layout.view.*

class MedicalRecordEntriesAdapter(
    val clickListener: OnImageCardClickListener,
    dataSet: List<MedicalRecordEntry> = emptyList(),
    private val context: Context
) : DragDropSwipeAdapter<MedicalRecordEntry, ViewHolder>(dataSet) {

    inner class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val imageView: ImageView = itemView.imageView
        val nameTextView: TextView = itemView.nameTextView

        init {
            itemView.setOnClickListener {
                clickListener.onImageCardClicked(layoutPosition, dataSet[layoutPosition], imageView)
            }
        }
    }

    override fun getViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun onBindViewHolder(item: MedicalRecordEntry, viewHolder: ViewHolder, position: Int) {
        viewHolder.nameTextView.text = item.name
        if (item.imageUrl == String.empty()) {

        } else {
            Glide.with(context)
                .load(item.imageUrl)
                .centerCrop()
                .into(viewHolder.imageView)
        }
    }

    override fun getViewToTouchToStartDraggingItem(
        item: MedicalRecordEntry,
        viewHolder: ViewHolder,
        position: Int
    ): View? {
        return viewHolder.imageView
    }

    interface OnImageCardClickListener {
        fun onImageCardClicked(position: Int, entry: MedicalRecordEntry, imageView: ImageView)
    }
}