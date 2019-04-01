package com.medicorum.Presentation.Signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.medicorum.Presentation.BaseFragment
import com.medicorum.databinding.FragmentFirstSignupBinding
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

        emailTextInput.validate(emailEditText, {s -> s.isEmailValid()}, "A valid email is required")
        passwordTextInput.validate(passwordEditText, {s -> s.isPasswordValid()}, "The password must have at least 8 characters")
        confirmPasswordTextInput.validate(confirmPasswordEditText, {s -> s == passwordEditText.text.toString()}, "The passwords do not match")
        firstNameTextInput.validate(firstNameEditText, {s -> s.isNotEmpty()}, "A valid name is required")
        lastNameTextInput.validate(lastNameEditText, {s -> s.isNotEmpty()}, "A valid name is required")
        phoneNumberTextInput.validate(phoneNumberEditText, {s -> s.isPhoneNumberValid()}, "A valid phone number is required")
    }

}