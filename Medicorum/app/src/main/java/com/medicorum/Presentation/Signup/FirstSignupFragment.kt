package com.medicorum.Presentation.Signup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicorum.Presentation.BaseFragment

import com.medicorum.R

class FirstSignupFragment : BaseFragment() {

    companion object {
        fun newInstance() = FirstSignupFragment()
    }

    private lateinit var viewModel: FirstSignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBottomBarVisibility(false)
        return inflater.inflate(R.layout.first_signup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FirstSignupViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
