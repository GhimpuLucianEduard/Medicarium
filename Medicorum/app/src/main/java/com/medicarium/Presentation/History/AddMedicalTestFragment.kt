package com.medicarium.Presentation.History

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Data.Enums.MedicalCategory
import com.medicarium.Presentation.BaseFragment
import com.medicarium.R
import empty
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import ir.mirrajabi.searchdialog.core.Searchable
import kotlinx.android.synthetic.main.add_medical_test_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.*
import java.util.*


class AddMedicalTestFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: AddMedicalTestViewModel
    private lateinit var categories: ArrayList<PickerValue>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_medical_test_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddMedicalTestViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {

        categories = ArrayList(MedicalCategory
            .values()
            .map { e -> PickerValue(e.name) })


        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(activity!!, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show()
        }

        categoryEditText.setOnClickListener {
            SimpleSearchDialogCompat(activity!!,
                "Select A Category",
                "Search...",
                null,
                categories,
                SearchResultListener<PickerValue> { dialog, value, _ ->
                    categoryEditText.setText(value.title)
                    dialog.dismiss()
                }).show()
        }

        customNavigationBar.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigateUp()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateEditText.setText("$dayOfMonth/$month/$year")
    }
}

class PickerValue(private val title: String = String.empty()) : Searchable {
    override fun getTitle(): String {
       return title
    }
}
