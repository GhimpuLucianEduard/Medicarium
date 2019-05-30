package com.medicorum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicarium.R


class GenericInfoFragment : Fragment() {

    companion object {
        fun newInstance() = GenericInfoFragment()
    }

    private lateinit var viewModel: GenericInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.generic_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GenericInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
