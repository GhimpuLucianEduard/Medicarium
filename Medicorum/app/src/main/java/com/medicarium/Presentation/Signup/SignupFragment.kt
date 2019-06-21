package com.medicarium.Presentation.Signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Presentation.BaseFragment
import com.medicarium.R
import com.medicarium.Utilities.ErrorMessages.Companion.INVALID_EMAIL
import com.medicarium.Utilities.ErrorMessages.Companion.INVALID_PASSWORD
import com.medicarium.Utilities.EventObserver
import com.medicarium.databinding.SignupFragmentBinding
import isEmailValid
import isPasswordValid
import isPhoneNumberValid
import kotlinx.android.synthetic.main.signup_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import validate

class SignupFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SignupViewModelFactory by instance()
    private lateinit var viewModel: SignupViewModel
    private lateinit var binding : SignupFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignupViewModel::class.java)

        binding = SignupFragmentBinding.inflate(inflater, container, false).apply {
            signUpViewModel = viewModel

            viewModel.navigateToSmsVerification.observe(this@SignupFragment, EventObserver {
                Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                    .navigate(SignupFragmentDirections.actionSignupFragmentToCheckSMSCodeFragment())
            })

            lifecycleOwner = this@SignupFragment
        }
        setBottomBarVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        continueButton.setOnClickListener {
            viewModel.signUp()
        }

        navigationBar.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigateUp()
        }

        viewModel.isBusy.observe(this@SignupFragment, Observer {
            if (it)
                setProgressBarVisibility(View.VISIBLE)
            else
                setProgressBarVisibility(View.GONE)
        })

        emailTextInput.validate(emailEditText, {s -> s.isEmailValid()}, INVALID_EMAIL)
        passwordTextInput.validate(passwordEditText, {s -> s.isPasswordValid()}, INVALID_PASSWORD)
        confirmPasswordTextInput.validate(confirmPasswordEditText, {s -> s == passwordEditText.text.toString()}, "The passwords do not match")
        firstNameTextInput.validate(firstNameEditText, {s -> s.isNotEmpty()}, "A valid name is required")
        lastNameTextInput.validate(lastNameEditText, {s -> s.isNotEmpty()}, "A valid name is required")
        phoneNumberTextInput.validate(phoneNumberEditText, {s -> s.isPhoneNumberValid()}, "A valid phone number is required")
    }

}