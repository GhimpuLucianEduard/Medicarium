package com.medicorum.Presentation.Signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.medicorum.Presentation.BaseFragment
import com.medicorum.databinding.FragmentFirstSignupBinding
import kotlinx.android.synthetic.main.fragment_first_signup.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FirstSignupFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SignUpViewModelFactory by instance()
    private lateinit var viewModel: FirstSignupViewModel
    private lateinit var binding : FragmentFirstSignupBinding

    companion object {
        fun newInstance() = FirstSignupFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java).firstSignUpViewModel

        binding = FragmentFirstSignupBinding.inflate(inflater, container, false).apply {
            firstSignUpViewModel = viewModel
        }

        binding.lifecycleOwner = this
        setBottomBarVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        continueButton.setOnClickListener {
            Log.i("User", viewModel.user.value.toString())
        }
    }

}