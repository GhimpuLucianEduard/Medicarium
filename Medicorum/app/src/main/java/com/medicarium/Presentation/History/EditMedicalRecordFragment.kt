package com.medicarium.Presentation.History

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Data.Enums.MedicalCategory
import com.medicarium.Data.Mappers.toMedicalRecord
import com.medicarium.R
import com.medicarium.databinding.EditMedicalRecordFragmentBinding
import deepClone
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.edit_medical_record_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class EditMedicalRecordFragment : Fragment(), KodeinAware, DatePickerDialog.OnDateSetListener {

    override val kodein by closestKodein()
    private val viewModelFactory: MedicalRecordDetailsViewModelFactory by instance()
    private lateinit var viewModel: MedicalRecordDetailsViewModel
    private lateinit var categories: ArrayList<AddMedicalRecordFragment.PickerValue>
    private lateinit var binding: EditMedicalRecordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MedicalRecordDetailsViewModel::class.java)

        binding = EditMedicalRecordFragmentBinding.inflate(inflater, container, false).apply {
            medicalRecordDetailsViewModel = viewModel
            lifecycleOwner = this@EditMedicalRecordFragment
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customNavigationBar.setLeftButtonClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigateUp()
        }

        categories = ArrayList(MedicalCategory
            .values()
            .map { e -> AddMedicalRecordFragment.PickerValue(e.name) })

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
                SearchResultListener<AddMedicalRecordFragment.PickerValue> { dialog, value, _ ->
                    //categoryEditText.setText(value.title)
                    viewModel.medicalRecordToEdit.value!!.medicalCategory = MedicalCategory.valueOf(value.title)
                    dialog.dismiss()
                }).show()
        }

        saveChangesButton.setOnClickListener {
            viewModel.cloneMedicalRecord.value!!.name = viewModel.medicalRecordToEdit.value!!.name
            viewModel.cloneMedicalRecord.value!!.timestamp = viewModel.medicalRecordToEdit.value!!.timestamp!!
            viewModel.cloneMedicalRecord.value!!.medicalCategory = viewModel.medicalRecordToEdit.value!!.medicalCategory!!
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigateUp()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        //dateEditText.setText("$dayOfMonth/$month/$year")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        viewModel.medicalRecordToEdit.value!!.timestamp = calendar.timeInMillis
    }
}
