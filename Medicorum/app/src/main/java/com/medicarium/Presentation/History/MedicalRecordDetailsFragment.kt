package com.medicarium.Presentation.History

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cloudinary.Cloudinary
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.signed.Signature
import com.cloudinary.android.signed.SignatureProvider
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.medicarium.Data.Mappers.toMedicalRecordObservable
import com.medicarium.Data.Models.MedicalRecordEntry
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.CustomControls.CustomNavigationBar
import com.medicarium.Presentation.Services.DialogService
import com.medicarium.R
import com.medicarium.databinding.MedicalRecordDetailsFragmentBinding
import com.stfalcon.imageviewer.StfalconImageViewer
import com.tbruyelle.rxpermissions2.RxPermissions
import deepClone
import empty
import kotlinx.android.synthetic.main.custom_navigation_bar.view.*
import kotlinx.android.synthetic.main.medical_record_details_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timestampToString
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MedicalRecordDetailsFragment : BaseFragment(), MedicalRecordEntriesAdapter.OnImageCardClickListener, KodeinAware {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2

    override val kodein by closestKodein()
    private val viewModelFactory: MedicalRecordDetailsViewModelFactory by instance()
    private val dialogService: DialogService by instance()
    private lateinit var adapter: MedicalRecordEntriesAdapter
    private lateinit var viewModel: MedicalRecordDetailsViewModel
    private lateinit var data: List<MedicalRecordEntry>
    private lateinit var overlayNavigationBar: CustomNavigationBar
    private lateinit var imageViewer: StfalconImageViewer<MedicalRecordEntry>
    private lateinit var binding: MedicalRecordDetailsFragmentBinding
    private var currentPosition: Int = 0
    private lateinit var rxPermissions: RxPermissions

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

        rxPermissions = RxPermissions(this)
        viewModel.onViewCreated()

        setupRecyclerView()

        viewModel.cloneMedicalRecord.observe(this@MedicalRecordDetailsFragment, Observer {
            navigationBar?.setTitletext(it.name)
            medicalCategoryTextView.text = it.medicalCategory.toString()
            dateTextView.text = it.timestamp.timestampToString()
            medicalCategoryImageView.setBackgroundResource(activity!!.resources.getIdentifier(it.medicalCategory.toString().toLowerCase(),
                "drawable", activity!!.packageName))

            adapter.dataSet = it.entries
        })

        navigationBar.setRightButtonClickListener {
            showToolbarMenu()
        }

        navigationBar.setLeftButtonClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigateUp()
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
                    onAddFromCamera()
                }
                R.id.fab_gallery -> {
                    onAddFromGallery()
                }
            }
            true
        }

    }

    private fun onAddFromGallery() {
        Intent(Intent.ACTION_OPEN_DOCUMENT).also {
            it.addCategory(Intent.CATEGORY_OPENABLE)
            it.type = "image/*"
            startActivityForResult(it, REQUEST_PICK_IMAGE)
        }
    }

    fun onAddFromCamera() {
        rxPermissions
            .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe {
                if (it) {
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {takePictureIntent ->
                        takePictureIntent.resolveActivity(activity!!.packageManager)?.also {

                            val photoFile: File? = try {
                                createImageFile()
                            } catch (ex: IOException) {
                                // TODO: handle errors
                                null
                            }

                            photoFile?.also {
                                val photoURI: Uri = FileProvider.getUriForFile(
                                    activity!!,
                                    "com.medicarium.fileprovider",
                                    it
                                )

                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                            }
                        }
                    }
                }
            }
    }

    private lateinit var currentPhotoPath: String


    @Throws(IOException::class)
    private fun createImageFile(): File {

        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = Environment.getExternalStorageDirectory()
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            sendImageToCloud(currentPhotoPath)
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            val uri = data?.data
            var path = String.empty()
            val wholeId = DocumentsContract.getDocumentId(uri)
            val id = wholeId.split(":")[1]
            val column = arrayOf(MediaStore.Images.Media.DATA)
            val sel = MediaStore.Images.Media._ID + "=?"
            val cursor = context!!.contentResolver
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, arrayOf(id), null)
            val colIndex = cursor.getColumnIndex(column[0])

            if (cursor.moveToFirst()) {
                path = cursor.getString(colIndex)
                sendImageToCloud(path)
            }

            cursor.close()
        }
    }


    private fun sendImageToCloud(uri: String) {
        MediaManager.get()
            .upload(uri)
            .callback(object : UploadCallback {
                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                    val doi = 2
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    val doi = 2
                }

                override fun onStart(requestId: String?) {
                    setProgressBarVisibility(View.VISIBLE)
                    addButton.hide()
                }

                override fun onSuccess(
                    requestId: String?,
                    resultData: MutableMap<Any?, Any?>?
                ) {
                    val url = resultData!!["url"]
                    setProgressBarVisibility(View.GONE)
                    addButton.show()

                    MaterialDialog(activity!!).show {
                        title(R.string.type_name)
                        input(hint = "Type the name here") { _, text ->
                            val entry = MedicalRecordEntry(
                                name = text.toString(),
                                imageUrl = url as String
                            )
                            viewModel.addNewEntry(entry)
                        }
                        positiveButton(R.string.ok_text_caps)
                        negativeButton(R.string.cancel_text_caps)
                    }

                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    setProgressBarVisibility(View.GONE)
                    addButton.show()
                }


            })
            .unsigned("cvudwnqd")
            .dispatch()
    }

    private fun setupRecyclerView() {

        adapter = MedicalRecordEntriesAdapter(this, emptyList(), activity!!)

        recyclerView.layoutManager = GridLayoutManager(activity!!, 2)
        recyclerView.adapter = adapter

        recyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.GRID_LIST_WITH_HORIZONTAL_SWIPING
        recyclerView.numOfColumnsPerRowInGridList = 2

        recyclerView.dividerDrawableId = null

        recyclerView.dragListener = object : OnItemDragListener<MedicalRecordEntry> {
            override fun onItemDragged(
                previousPosition: Int,
                newPosition: Int,
                item: MedicalRecordEntry
            ) {

            }

            override fun onItemDropped(
                initialPosition: Int,
                finalPosition: Int,
                item: MedicalRecordEntry
            ) {
                viewModel.cloneMedicalRecord.value!!.entries = adapter.dataSet
            }

        }

    }

    override fun onImageCardClicked(position: Int, entry: MedicalRecordEntry, imageView: ImageView) {

        currentPosition = position
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

        imageViewer = StfalconImageViewer.Builder<MedicalRecordEntry>(context, adapter.dataSet) { view, item ->
            Glide.with(activity!!)
                .load(MediaManager.get().url().generate(item.imageUrl))
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
                            title(R.string.type_name)
                            input(hint = "Type the new name here") { _, text ->
                                Log.i("TEST", "current positoion: $currentPosition")

                                viewModel.cloneMedicalRecord.value!!.entries[currentPosition].name = text.toString()

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
                    callback = {
                        imageViewer.dismiss()
                        viewModel.removeEntry(currentPosition)
                    }
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
                        onSaveChangedClicked()
                    }
                }
                item {
                    label = "Edit informations"
                    icon = R.drawable.ic_edit_black_24dp
                    labelColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    iconColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    callback = {

                        viewModel.medicalRecordToEdit.value = viewModel.cloneMedicalRecord.value!!.deepClone().toMedicalRecordObservable()
                        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                            .navigate(MedicalRecordDetailsFragmentDirections.actionMedicalRecordDetailsFragmentToEditMedicalRecordFragment())
                    }
                }
                item {
                    label = "Delete"
                    icon = R.drawable.ic_delete_red_24dp
                    labelColor = ContextCompat.getColor(activity!!, R.color.primaryBlack)
                    iconColor = ContextCompat.getColor(activity!!, R.color.accentRed)
                    callback = {
                        dialogService.showConfirmationAlertWithIcon(
                            activity!!,
                            "Delete Medical Record",
                            "Are you sure you want to delete this record?",
                            R.drawable.error_dialog,
                            "Yes",
                            "No",
                            DialogInterface.OnClickListener { _, _ ->  onDeleteMedicalRecord()},
                            DialogInterface.OnClickListener { _, _ ->  }
                        )
                    }
                }
            }
        }.show(activity!!, navigationBar.rightImageButton)
    }

    private fun onDeleteMedicalRecord() {
        viewModel.deleteMedicalRecord()
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigateUp()
    }

    private fun onSaveChangedClicked() {
        viewModel.saveChanges()
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigateUp()
    }

}
