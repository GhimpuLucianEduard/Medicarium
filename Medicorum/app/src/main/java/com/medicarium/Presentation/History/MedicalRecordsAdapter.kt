package com.medicarium.Presentation.History

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.Data.Models.MedicalRecordEntry
import com.medicarium.R
import kotlinx.android.synthetic.main.medical_record_layout.view.*
import timestampToString

class MedicalRecordsAdapter(
    dataset: List<MedicalRecord> = emptyList(),
    private val context: Context,
    private val cardClickListener: MedicalRecordsAdapter.OnMedicalCardClickListener
) : DragDropSwipeAdapter<MedicalRecord, MedicalRecordsAdapter.ViewHolder>(dataset) {

    fun setItems(data: List<MedicalRecord>) {
        dataSet = data.sortedByDescending {
            it.timestamp
        }
    }

    inner class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.nameTextView
        val dateTextView: TextView = itemView.dateTextView
        val categoryTextView: TextView = itemView.categoryTextView
        val categoryImageView: ImageView = itemView.categoryImageView
        val previewImageView: ImageView = itemView.previewImageView

        init {
            itemView.setOnClickListener {
                cardClickListener.onMedicalRecordClicked(dataSet[layoutPosition])
            }
        }
    }

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    override fun onBindViewHolder(item: MedicalRecord, viewHolder: MedicalRecordsAdapter.ViewHolder, position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        viewHolder.nameTextView.text = item.name
        viewHolder.dateTextView.text = item.timestamp.timestampToString()
        viewHolder.categoryTextView.text = item.medicalCategory.toString().toLowerCase().capitalize()
        viewHolder.categoryImageView.setBackgroundResource(context.resources.getIdentifier(item.medicalCategory.toString().toLowerCase(),
            "drawable", context.packageName))

        Glide.with(context)
            .load("https://picsum.photos/200/300/?random")
            .centerCrop()
            .placeholder(R.drawable.picture)
            .into(viewHolder.previewImageView)
    }

    override fun getViewToTouchToStartDraggingItem(item: MedicalRecord, viewHolder: MedicalRecordsAdapter.ViewHolder, position: Int): View? {
        // We return the view holder's view on which the user has to touch to drag the item
        return viewHolder.categoryImageView
    }

    interface OnMedicalCardClickListener {
        fun onMedicalRecordClicked(record: MedicalRecord)
    }
}