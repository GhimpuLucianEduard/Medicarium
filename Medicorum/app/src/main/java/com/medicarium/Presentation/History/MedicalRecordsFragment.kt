package com.medicarium.Presentation.History

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.medicarium.Data.Enums.MedicalCategory
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.Services.DialogService
import com.medicarium.R
import com.medicarium.databinding.MedicalRecordsFragmentBinding
import kotlinx.android.synthetic.main.medical_records_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class MedicalRecordsFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val dialogService: DialogService by instance()
    private val viewModelFactory: MedicalRecordsViewModelFactory by instance()
    private lateinit var viewModel: MedicalRecordsViewModel
    private lateinit var binding: MedicalRecordsFragmentBinding
    private lateinit var adapter: MedicalRecordsAdapter

    companion object {
        fun newInstance() = MedicalRecordsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MedicalRecordsViewModel::class.java)

        binding = MedicalRecordsFragmentBinding.inflate(inflater, container, false).apply {
            medicalRecordsViewModel = viewModel

            lifecycleOwner = this@MedicalRecordsFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        viewModel.isBusy.observe(this@MedicalRecordsFragment, androidx.lifecycle.Observer {
            if (it)
                setProgressBarVisibility(View.VISIBLE)
            else
                setProgressBarVisibility(View.GONE)
        })

        addButton.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(MedicalRecordsFragmentDirections.actionMedicalTestsFragmentToAddMedicalTestFragment())
        }

    }

    private fun setUpRecyclerView() {

        adapter = MedicalRecordsAdapter(emptyList(), activity!!)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = adapter

        viewModel.medicalRecords.observe(this@MedicalRecordsFragment, Observer {
            adapter.setItems(it)
        })

        recyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
        recyclerView.orientation?.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)

        recyclerView.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
        recyclerView.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.LEFT)
        recyclerView.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.UP)
        recyclerView.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.DOWN)

        recyclerView.reduceItemAlphaOnSwiping = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            recyclerView.defaultFocusHighlightEnabled = true
        }

        recyclerView.scrollListener = object : OnListScrollListener {
            override fun onListScrolled(
                scrollDirection: OnListScrollListener.ScrollDirection,
                distance: Int
            ) {
                Log.i("REQ_API", "Scroldir: ${scrollDirection}, distcance: $distance")

                if (scrollDirection == OnListScrollListener.ScrollDirection.DOWN) {
                    addButton.hide()
                } else {
                    addButton.show()
                }
            }
        }


        recyclerView.swipeListener = object : OnItemSwipeListener<MedicalRecord> {
            override fun onItemSwiped(
                position: Int,
                direction: OnItemSwipeListener.SwipeDirection,
                item: MedicalRecord
            ): Boolean {
                dialogService.showConfirmationAlertWithIcon(
                    activity!!,
                    "Title",
                    "Are you sure you want to delete this item?",
                    R.drawable.error_dialog,
                    "Yes",
                    "No",
                    DialogInterface.OnClickListener { _, _ -> viewModel.deleteMedicalRecord(item.id)},
                    DialogInterface.OnClickListener { _, _ -> adapter.notifyDataSetChanged()}
                )

                return true
            }

        }
    }

}
