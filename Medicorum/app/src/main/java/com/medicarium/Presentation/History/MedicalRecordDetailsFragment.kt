package com.medicarium.Presentation.History

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.bumptech.glide.Glide
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.medicarium.Data.Models.MedicalRecordEntry
import com.medicarium.Presentation.CustomControls.CustomNavigationBar
import com.medicarium.R
import com.medicarium.databinding.MedicalRecordDetailsFragmentBinding
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.custom_navigation_bar.view.*
import kotlinx.android.synthetic.main.medical_record_details_fragment.*
import org.jetbrains.anko.support.v4.act
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timestampToString

class MedicalRecordDetailsFragment : Fragment(), MedicalRecordEntriesAdapter.OnImageCardClickListener, KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: MedicalRecordDetailsViewModelFactory by instance()
    private lateinit var adapter: MedicalRecordEntriesAdapter
    private lateinit var viewModel: MedicalRecordDetailsViewModel
    private lateinit var data: List<MedicalRecordEntry>
    private lateinit var overlayNavigationBar: CustomNavigationBar
    private lateinit var binding: MedicalRecordDetailsFragmentBinding
    private var currentPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MedicalRecordDetailsViewModel::class.java)

        binding = MedicalRecordDetailsFragmentBinding.inflate(inflater, container, false).apply {
            medicalRecordDetailsViewModel = viewModel
            lifecycleOwner = this@MedicalRecordDetailsFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.cloneMedicalRecord.observe(this@MedicalRecordDetailsFragment, Observer {
            navigationBar?.setTitletext(it.name)
            medicalCategoryTextView.text = it.medicalCategory.toString()
            dateTextView.text = it.timestamp.timestampToString()
            medicalCategoryImageView.setBackgroundResource(activity!!.resources.getIdentifier(it.medicalCategory.toString().toLowerCase(),
                "drawable", activity!!.packageName))
        })

        navigationBar.setRightButtonClickListener {
            showToolbarMenu()
        }

        addButton.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_camera, R.drawable.ic_camera_white_24dp)
                .setFabBackgroundColor(resources.getColor(R.color.accentBlue))
                .setLabel("Take a photo")
                .create()
        )

        addButton.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_gallery, R.drawable.ic_folder_white_24dp)
                .setFabBackgroundColor(resources.getColor(R.color.accentRed))
                .setLabel("From gallery")
                .create()
        )

        addButton.setOnActionSelectedListener {
            when(it.id) {
                R.id.fab_camera -> {
                    startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                }
            }
            true
        }

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

        overlayNavigationBar = CustomNavigationBar(activity!!).also {
            it.setLeftImageDrawnable(activity!!.getDrawable(R.drawable.close_black)!!)
            it.setRightImageDrawnable(activity!!.getDrawable(R.drawable.more_verti)!!)
            it.setTitletext(entry.name)
            it.setBackgroundColor(resources.getColor(R.color.pureWhite))
        }

        val overLay = LinearLayout(activity!!).also {
            overlayNavigationBar.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            it.addView(overlayNavigationBar)
        }

        val imageViewer = StfalconImageViewer.Builder<MedicalRecordEntry>(context, adapter.dataSet) { view, item ->
            Glide.with(activity!!)
                .load(item.imageUrl)
                .centerCrop()
                .into(view)
            }
            .withStartPosition(position)
            .withOverlayView(overLay)
            .withImageMarginPixels(24)
            .withImageChangeListener {
                overlayNavigationBar.setTitletext(adapter.dataSet[it].name)
                currentPosition = it
            }
            .withDismissListener {
                adapter.notifyDataSetChanged()
            }
            .withTransitionFrom(imageView)
            .show()

        overlayNavigationBar.setLeftButtonClickListener {
            imageViewer.dismiss()
        }

        overlayNavigationBar.setRightButtonClickListener {
            setupMenu(overlayNavigationBar.rightImageButton)
        }
    }

    private fun setupMenu(anchorView: View) {
        popupMenu {
            section {
                item {
                    label = "Rename"
                    labelColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    icon = R.drawable.ic_edit_black_24dp
                    iconColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    callback = {
                        MaterialDialog(activity!!).show {
                            input(hint = "Type the new name here") { _, text ->
                                adapter.dataSet[currentPosition].name = text.toString()
                                overlayNavigationBar.setTitletext(text.toString())
                            }
                            positiveButton(R.string.ok_text_caps)
                            negativeButton(R.string.cancel_text_caps)
                        }
                    }
                }
                item {
                    label = "Delete"
                    labelColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    icon = R.drawable.ic_delete_red_24dp
                    iconColor = ContextCompat.getColor(activity!!, R.color.accentRed)
                }
            }
        }.show(activity!!, anchorView)
    }

    private fun showToolbarMenu() {
        popupMenu {
            section {
                item {
                    label = "Save Changes"
                    icon = R.drawable.ic_save_black_24dp
                    labelColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    iconColor = ContextCompat.getColor(activity!!, R.color.accentBlue)
                    callback = {

                    }
                }
                item {
                    label = "Edit informations"
                    icon = R.drawable.ic_edit_black_24dp
                    labelColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    iconColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    callback = {

                    }
                }
                item {
                    label = "Delete"
                    icon = R.drawable.ic_delete_red_24dp
                    labelColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    iconColor = ContextCompat.getColor(activity!!, R.color.accentRed)
                    callback = {

                    }
                }
            }
        }.show(activity!!, navigationBar.rightImageButton)
    }
}
