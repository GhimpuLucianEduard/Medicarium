package com.medicarium.Presentation.History

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.medicorum.R

class MedicalRecordDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MedicalRecordDetailsFragment()
    }

    private lateinit var viewModel: MedicalRecordDetailsViewModel

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

}
