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
import com.medicarium.Presentation.DataViewModels.MedicalRecordObservable
import com.medicarium.R
import com.medicarium.Utilities.EventObserver
import com.medicarium.databinding.AddMedicalRecordFragmentBinding
import empty
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import ir.mirrajabi.searchdialog.core.Searchable
import kotlinx.android.synthetic.main.add_medical_record_fragment.*
import org.jetbrains.anko.support.v4.act
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*


class AddMedicalRecordFragment : BaseFragment(), KodeinAware, DatePickerDialog.OnDateSetListener {

    override val kodein by closestKodein()
    private val viewModelFactory: MedicalRecordsViewModelFactory by instance()
    private lateinit var viewModel: MedicalRecordsViewModel
    private lateinit var categories: ArrayList<PickerValue>
    private lateinit var binding: AddMedicalRecordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MedicalRecordsViewModel::class.java)

        binding = AddMedicalRecordFragmentBinding.inflate(inflater, container, false).apply {
            medicalRecordsViewModel = viewModel
            viewModel.medicalRecordToAdd.value = MedicalRecordObservable()
            lifecycleOwner = this@AddMedicalRecordFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {

        viewModel.isBusy.observe(this@AddMedicalRecordFragment, androidx.lifecycle.Observer {
            if (it)
                setProgressBarVisibility(View.VISIBLE)
            else
                setProgressBarVisibility(View.GONE)
        })

        viewModel.navigateBack.observe(this@AddMedicalRecordFragment, EventObserver {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigateUp()
        })

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
                    //categoryEditText.setText(value.title)
                    viewModel.medicalRecordToAdd.value!!.medicalCategory = MedicalCategory.valueOf(value.title)
                    dialog.dismiss()
                }).show()
        }

        customNavigationBar.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigateUp()
        }

        addRecordButton.setOnClickListener {
            viewModel.addMedicalRecord()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        //dateEditText.setText("$dayOfMonth/$month/$year")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        viewModel.medicalRecordToAdd.value!!.timestamp = calendar.timeInMillis
    }


    class PickerValue(private val title: String = String.empty()) : Searchable {
        override fun getTitle(): String {
            return title
        }
    }
}

