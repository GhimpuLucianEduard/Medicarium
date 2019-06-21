package com.medicarium.Presentation.General


import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.medicarium.Data.Enums.BloodType
import com.medicarium.Data.Enums.Gender
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.History.AddMedicalRecordFragment
import com.medicarium.Presentation.Services.DialogService
import com.medicarium.Presentation.Services.ToastService
import com.medicarium.R
import com.medicarium.databinding.FragmentGenericInfoBinding
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.fragment_generic_info.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class GenericInfoFragment : BaseFragment(), KodeinAware, DatePickerDialog.OnDateSetListener {

    override val kodein by closestKodein()
    private val viewModelFactory: GenericInfoViewModelFactory by instance()
    private lateinit var viewModel: GenericInfoViewModel
    private lateinit var binding: FragmentGenericInfoBinding

    private lateinit var bloodTypes: ArrayList<AddMedicalRecordFragment.PickerValue>
    private lateinit var gendres: ArrayList<AddMedicalRecordFragment.PickerValue>

    private val toastService: ToastService by instance()
    private val dialogService: DialogService by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setBottomBarVisibility(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GenericInfoViewModel::class.java)
        binding = FragmentGenericInfoBinding.inflate(inflater, container, false).apply {
            genericInfoViewModel = viewModel
            lifecycleOwner = this@GenericInfoFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserData()

        saveLabel.setOnClickListener {
            dialogService.showConfirmationAlertWithIcon(
                context = activity!!,
                title = "Are you sure you want to save these changes?",
                message = "Please be adviced, these changes will override your current medical information.",
                icon = R.drawable.error_dialog,
                positiveButtonText = "Save Changes",
                negativeButtonText = "Reset Changes",
                positiveClickListener = DialogInterface.OnClickListener { _, _->
                    viewModel.updateUserData()
                },
                negativeClickListener = DialogInterface.OnClickListener { _, _->
                    viewModel.resetUser()
                }
            )
        }

        viewModel.isBusy.observe(this@GenericInfoFragment, Observer {
            if (it)
                setProgressBarVisibility(View.VISIBLE)
            else
                setProgressBarVisibility(View.GONE)
        })

        setupImageSwitchers()

        bloodTypes = ArrayList(
            BloodType
                .values()
                .map { e -> AddMedicalRecordFragment.PickerValue(e.name) })

        gendres = ArrayList(
            Gender
            .values()
            .map { e -> AddMedicalRecordFragment.PickerValue(e.name) })
    }

    private fun setupImageSwitchers() {

        if (!viewModel.user.value!!.isFirstNameVisible) {
            (firstNameImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        firstNameImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isFirstNameVisible = viewModel.user.value!!.isFirstNameVisible.not()
        }

        nameEditText.setOnClickListener {
            MaterialDialog(activity!!).show {
                title(R.string.type_first_name)
                input(hint = "Type the first name here") { _, text ->
                    viewModel.user.value!!.firstName = text.toString()
                }
                positiveButton(R.string.ok_text_caps)
                negativeButton(R.string.cancel_text_caps)
            }
        }

        if (!viewModel.user.value!!.isLastNameVisible) {
            (lastNameImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        lastNameImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isLastNameVisible = viewModel.user.value!!.isLastNameVisible.not()
        }

        lastNameEditText.setOnClickListener {
            MaterialDialog(activity!!).show {
                title(R.string.type_last_name)
                input(hint = "Type the last name here") { _, text ->
                    viewModel.user.value!!.lastName = text.toString()
                }
                positiveButton(R.string.ok_text_caps)
                negativeButton(R.string.cancel_text_caps)
            }
        }

        if (!viewModel.user.value!!.isPhoneNumberVisible) {
            (phoneNumberImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        phoneNumberImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isPhoneNumberVisible = viewModel.user.value!!.isPhoneNumberVisible.not()
        }

        phoneEditText.setOnClickListener {
            MaterialDialog(activity!!).show {
                title(R.string.phone_number_hint)
                input(hint = "Type the number here", inputType = InputType.TYPE_CLASS_PHONE) { _, text ->
                    viewModel.user.value!!.phoneNumber = text.toString()
                }
                positiveButton(R.string.ok_text_caps)
                negativeButton(R.string.cancel_text_caps)
            }
        }

        if (!viewModel.user.value!!.isBirthDateVisible) {
            (birthDAteImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        birthDAteImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isBirthDateVisible = viewModel.user.value!!.isBirthDateVisible.not()
        }

        birthDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(activity!!, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show()
        }


        if (!viewModel.user.value!!.isWeightVisible) {
            (weightImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        weightImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isWeightVisible = viewModel.user.value!!.isWeightVisible.not()
        }

        weightEditText.setOnClickListener {
            MaterialDialog(activity!!).show {
                title(R.string.type_weight)
                input(hint = "Type the number here", inputType = InputType.TYPE_NUMBER_VARIATION_NORMAL) { _, text ->
                    viewModel.user.value!!.weight = text.toString()
                }
                positiveButton(R.string.ok_text_caps)
                negativeButton(R.string.cancel_text_caps)
            }
        }

        if (!viewModel.user.value!!.isHeightVisible) {
            (heightImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        heightImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isHeightVisible = viewModel.user.value!!.isHeightVisible.not()
        }

        heightEditText.setOnClickListener {
            MaterialDialog(activity!!).show {
                title(R.string.type_height)
                input(hint = "Type the number here", inputType = InputType.TYPE_NUMBER_VARIATION_NORMAL) { _, text ->
                    viewModel.user.value!!.height = text.toString()
                }
                positiveButton(R.string.ok_text_caps)
                negativeButton(R.string.cancel_text_caps)
            }
        }


        if (!viewModel.user.value!!.isBloodTypeVisible) {
            (bloodTypeImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        bloodTypeImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isBloodTypeVisible = viewModel.user.value!!.isBloodTypeVisible.not()
        }

        bloodTypeEditText.setOnClickListener {
            SimpleSearchDialogCompat(activity!!,
                "Select your blood type",
                "Search...",
                null,
                bloodTypes,
                SearchResultListener<AddMedicalRecordFragment.PickerValue> { dialog, value, _ ->
                    viewModel.user.value!!.bloodType = BloodType.valueOf(value.title)
                    dialog.dismiss()
                }).show()
        }

        if (!viewModel.user.value!!.isEmergencyContactNameVisible) {
            (emergencyContactImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        emergencyContactImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isEmergencyContactNameVisible = viewModel.user.value!!.isEmergencyContactNameVisible.not()
        }

        emergencyContactEditText.setOnClickListener {
            MaterialDialog(activity!!).show {
                title(R.string.type_name)
                input(hint = "Type the name here") { _, text ->
                    viewModel.user.value!!.emergencyContact = text.toString()
                }
                positiveButton(R.string.ok_text_caps)
                negativeButton(R.string.cancel_text_caps)
            }
        }


        if (!viewModel.user.value!!.isEmergencyContactPhoneNumberVisible) {
            (emergencyNumberImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        emergencyNumberImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isEmergencyContactPhoneNumberVisible = viewModel.user.value!!.isEmergencyContactPhoneNumberVisible.not()
        }

        emergencyNumberEditText.setOnClickListener {
            MaterialDialog(activity!!).show {
                title(R.string.phone_number_hint)
                input(hint = "Type the number here", inputType = InputType.TYPE_CLASS_PHONE) { _, text ->
                    viewModel.user.value!!.emergencyNumber = text.toString()
                }
                positiveButton(R.string.ok_text_caps)
                negativeButton(R.string.cancel_text_caps)
            }
        }

        if (!viewModel.user.value!!.isGenderVisible) {
            (genderImageSwitcher.background as TransitionDrawable).reverseTransition(0)
        }
        genderImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
            viewModel.user.value!!.isGenderVisible = viewModel.user.value!!.isGenderVisible.not()
        }

        genderEditText.setOnClickListener {
            SimpleSearchDialogCompat(activity!!,
                "Select your gender",
                "Search...",
                null,
                gendres,
                SearchResultListener<AddMedicalRecordFragment.PickerValue> { dialog, value, _ ->
                    viewModel.user.value!!.gendre = Gender.valueOf(value.title)
                    dialog.dismiss()
                }).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        //dateEditText.setText("$dayOfMonth/$month/$year")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        viewModel.user.value!!.birthDate = calendar.timeInMillis
    }
}
