package com.medicarium.Presentation.History

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.medicarium.Data.Enums.MedicalCategory
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.Login.LoginFragmentDirections
import com.medicarium.Presentation.Services.DialogService
import com.medicarium.R
import kotlinx.android.synthetic.main.medical_tests_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class MedicalTestsFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val dialogService: DialogService by instance()
    private lateinit var adapter: MedicalTestsAdapter
    private lateinit var data: List<MedicalRecord>

    companion object {
        fun newInstance() = MedicalTestsFragment()
    }

    private lateinit var viewModel: MedicalTestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.medical_tests_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = listOf(
            MedicalRecord("1", "Some medical record1", MedicalCategory.PSYCHOLOGY, 2131414),
            MedicalRecord("2", "Some medical record12", MedicalCategory.PULMONOLOGY, 2131414),
            MedicalRecord("3", "Some medical record1", MedicalCategory.DERMATOLOGY, 2131414),
            MedicalRecord("4", "Some medical record1", MedicalCategory.RADIOLOGY, 2131414),
            MedicalRecord("5", "Some medical record1", MedicalCategory.IMMUNOLOGY, 2131414),
            MedicalRecord("6", "Some medical record1", MedicalCategory.DENTISTRY, 2131414),
            MedicalRecord("7", "Some medical record1", MedicalCategory.HEMATOLOGY, 2131414),
            MedicalRecord("8", "Some medical record1", MedicalCategory.CARDIOLOGY, 2131414),
            MedicalRecord("9", "Some medical record1", MedicalCategory.OPHTHALMOLOGY, 2131414),
            MedicalRecord("10", "Some medical record1", MedicalCategory.OTOLOGY, 2131414)
        )
        adapter = MedicalTestsAdapter(data, activity!!)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = adapter

        recyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
        recyclerView.orientation?.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)

        recyclerView.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
        recyclerView.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.LEFT)
        recyclerView.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.UP)
        recyclerView.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.DOWN)

        recyclerView.reduceItemAlphaOnSwiping = true

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

        addButton.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(MedicalTestsFragmentDirections.actionMedicalTestsFragmentToAddMedicalTestFragment())
            true
        }

//        recyclerView.swipeListener = object : OnItemSwipeListener<String> {
//            override fun onItemSwiped(
//                position: Int,
//                direction: OnItemSwipeListener.SwipeDirection,
//                item: String
//            ): Boolean {
//
//                var rez = true
//
//                dialogService.showConfirmationAlertWithIcon(
//                    activity!!,
//                    "Title",
//                    "Are you sure you want to delete this item?",
//                    R.drawable.error_dialog,
//                    "Yes",
//                    "No",
//                    DialogInterface.OnClickListener { _, _ -> rez = false},
//                    DialogInterface.OnClickListener { _, _ -> rez = true}
//                )
//
//                return rez
//            }
//
//        }

    }

}
