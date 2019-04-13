package com.medicarium.Presentation.History

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.R
import kotlinx.android.synthetic.main.medical_test_layout.view.*

class MedicalTestsAdapter(
    dataset: List<MedicalRecord> = emptyList(),
    private val context: Context
) : DragDropSwipeAdapter<MedicalRecord, MedicalTestsAdapter.ViewHolder>(dataset) {

    class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.nameTextView
        val dateTextView: TextView = itemView.dateTextView
        val categoryTextView: TextView = itemView.categoryTextView
        val categoryImageView: ImageView = itemView.categoryImageView
        val previewImageView: ImageView = itemView.previewImageView
    }

    override fun getViewHolder(itemView: View) = MedicalTestsAdapter.ViewHolder(itemView)

    override fun onBindViewHolder(item: MedicalRecord, viewHolder: MedicalTestsAdapter.ViewHolder, position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        viewHolder.nameTextView.text = item.name
        viewHolder.dateTextView.text = item.timestamp.toString()
        viewHolder.categoryTextView.text = item.medicalCategory.toString().toLowerCase().capitalize()
        viewHolder.categoryImageView.setBackgroundResource(context.resources.getIdentifier(item.medicalCategory.toString().toLowerCase(),
            "drawable", context.packageName))

        Glide.with(context)
            .load("https://picsum.photos/200/300/?random")
            .centerCrop()
            .placeholder(R.drawable.picture)
            .into(viewHolder.previewImageView)
    }

    override fun getViewToTouchToStartDraggingItem(item: MedicalRecord, viewHolder: MedicalTestsAdapter.ViewHolder, position: Int): View? {
        // We return the view holder's view on which the user has to touch to drag the item
        return viewHolder.nameTextView
    }

}