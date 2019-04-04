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
import com.medicarium.databinding.FragmentFirstSignupBinding
import isEmailValid
import isPasswordValid
import isPhoneNumberValid
import kotlinx.android.synthetic.main.fragment_first_signup.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import validate

class FirstSignupFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SignUpViewModelFactory by instance()
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding : FragmentFirstSignupBinding

    companion object {
        fun newInstance() = FirstSignupFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding = FragmentFirstSignupBinding.inflate(inflater, container, false).apply {
            signUpViewModel = viewModel

            viewModel.navigateToSmsVerification.observe(this@FirstSignupFragment, EventObserver {
                Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                    .navigate(FirstSignupFragmentDirections.actionFirstSignupFragmentToCheckSMSCodeFragment())
            })

            lifecycleOwner = this@FirstSignupFragment
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

        viewModel.isBusy.observe(this@FirstSignupFragment, Observer {
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