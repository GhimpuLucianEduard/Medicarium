package com.medicarium.Presentation.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.medicarium.R
import kotlinx.android.synthetic.main.medical_tests_fragment.*

class MedicalTestsFragment : Fragment() {

    private lateinit var adapter: MedicalTestsAdapter
    private lateinit var data: List<String>

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
        data = listOf("Analiza ogj", "Radiografie plaman drept", "Analize sange", "Nu stiu exact ce e", "Ce poze", "Glicemii", "Altceva", "Chair nu stiu ce sa mai pun", "aaa", "sal pa?",
            "Nu stiu exact ce e", "Ce poze", "Glicemii", "Altceva", "Nu stiu exact ce e", "Ce poze", "Glicemii", "Altceva", "Nu stiu exact ce e", "Ce poze", "Glicemii", "Altceva")
        adapter = MedicalTestsAdapter(data)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = adapter

        recyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
    }

}
