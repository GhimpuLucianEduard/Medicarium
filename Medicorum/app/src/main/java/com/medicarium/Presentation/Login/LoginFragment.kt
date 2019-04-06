package com.medicarium.Presentation.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.Signup.SignupViewModel
import com.medicarium.Presentation.Signup.SignupViewModelFactory
import com.medicarium.R
import com.medicarium.Utilities.ErrorMessages.Companion.INVALID_EMAIL
import com.medicarium.Utilities.ErrorMessages.Companion.INVALID_PASSWORD
import com.medicarium.Utilities.EventObserver
import com.medicarium.databinding.LoginFragmentBinding
import com.medicarium.databinding.SignupFragmentBinding
import isEmailValid
import isPasswordValid
import kotlinx.android.synthetic.main.login_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import validate

class LoginFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: LoginViewModelFactory by instance()
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding : LoginFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        binding = LoginFragmentBinding.inflate(inflater, container, false).apply {
            loginViewModel = viewModel

            viewModel.navigateToPinSetup.observe(this@LoginFragment, EventObserver {
                Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                    .navigate(LoginFragmentDirections.actionLoginFragmentToSetupPinFragment())
            })

            lifecycleOwner = this@LoginFragment
        }

        setBottomBarVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {

        emailTextInput.validate(emailEditText, {s -> s.isEmailValid()}, INVALID_EMAIL)
        passwordTextInput.validate(passwordEditText, {s -> s.isPasswordValid()}, INVALID_PASSWORD)

        registerTextView.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(LoginFragmentDirections.actionLoginFragmentToFirstSignupFragment())
        }

        loginButton.setOnClickListener {
            viewModel.handleLoginClicked()
        }
    }

}
