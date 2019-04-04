package com.medicarium.Presentation.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Presentation.BaseFragment
import com.medicarium.R
import com.medicarium.Utilities.ErrorMessages.Companion.INVALID_EMAIL
import isEmailValid
import isPasswordValid
import kotlinx.android.synthetic.main.login_fragment.*
import validate

class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        setBottomBarVisibility(false)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        emailTextInput.validate(emailEditText, {s -> s.isEmailValid()}, INVALID_EMAIL)
        passwordTextInput.validate(passwordEditText, {s -> s.isPasswordValid()}, INVALID_EMAIL)
        registerTextView.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(LoginFragmentDirections.actionLoginFragmentToFirstSignupFragment())
        }
    }

}
