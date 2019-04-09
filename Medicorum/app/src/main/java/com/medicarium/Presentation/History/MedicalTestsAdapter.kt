package com.medicarium.Presentation.History

import android.view.View
import android.widget.TextView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import kotlinx.android.synthetic.main.medical_test_layout.view.*

class MedicalTestsAdapter(
    dataset: List<String> = emptyList()
) : DragDropSwipeAdapter<String, MedicalTestsAdapter.ViewHolder>(dataset) {

    class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.nameTextView
    }

    override fun getViewHolder(itemView: View) = MedicalTestsAdapter.ViewHolder(itemView)

    override fun onBindViewHolder(item: String, viewHolder: MedicalTestsAdapter.ViewHolder, position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        viewHolder.nameTextView.text = item
    }

    override fun getViewToTouchToStartDraggingItem(item: String, viewHolder: MedicalTestsAdapter.ViewHolder, position: Int): View? {
        // We return the view holder's view on which the user has to touch to drag the item
        return viewHolder.nameTextView
    }

}