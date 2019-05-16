package com.medicarium.Presentation.History

import android.media.Image
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.medicarium.Data.Models.MedicalRecordEntry
import com.medicarium.Presentation.CustomControls.CustomNavigationBar
import com.medicarium.R
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.medical_records_fragment.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.act

class MedicalRecordDetailsFragment : Fragment(), MedicalRecordEntriesAdapter.OnImageCardClickListener {

    private lateinit var adapter: MedicalRecordEntriesAdapter
    private lateinit var viewModel: MedicalRecordDetailsViewModel
    private lateinit var data: List<MedicalRecordEntry>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.medical_record_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MedicalRecordDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        data = listOf(
            MedicalRecordEntry("das", "Poza 1", "https://picsum.photos/id/237/1920/1080"),
            MedicalRecordEntry("das", "Poza 2", "https://picsum.photos/id/197/1920/1080"),
            MedicalRecordEntry("das", "Poza 3", "https://picsum.photos/id/107/1920/1080"),
            MedicalRecordEntry("das", "Poza 4", "https://picsum.photos/id/257/1920/1080")
        )


        adapter = MedicalRecordEntriesAdapter(this, data, activity!!)

        recyclerView.layoutManager = GridLayoutManager(activity!!, 2)
        recyclerView.adapter = adapter

        recyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.GRID_LIST_WITH_HORIZONTAL_SWIPING
        recyclerView.numOfColumnsPerRowInGridList = 2

        recyclerView.dividerDrawableId = null

    }

    override fun onImageCardClicked(position: Int, entry: MedicalRecordEntry, imageView: ImageView) {

        val overlay = CustomNavigationBar(activity!!).also {
            it.setLeftImageDrawnable(activity!!.getDrawable(R.drawable.close_black)!!)
            it.setTitletext(entry.name)
        }

        val imageViewer = StfalconImageViewer.Builder<MedicalRecordEntry>(context, adapter.dataSet) { view, item ->
            Glide.with(activity!!)
                .load(item.imageUrl)
                .centerCrop()
                .into(view)
            }
            .withStartPosition(position)
            .withOverlayView(overlay)
            .withImageChangeListener {
                overlay.setTitletext(adapter.dataSet[it].name)
            }
            .withTransitionFrom(imageView)
            .show()

        overlay.setLeftButtonClickListener {
            imageViewer.dismiss()
        }
    }

}
