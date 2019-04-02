package com.medicorum.Presentation.Signup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicorum.Presentation.BaseFragment

import com.medicorum.databinding.FragmentFirstSignupBinding
import kotlinx.android.synthetic.main.check_smscode_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CheckSMSCodeFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SignUpViewModelFactory by instance()
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding : FragmentFirstSignupBinding

    companion object {
        fun newInstance() = CheckSMSCodeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding = FragmentFirstSignupBinding.inflate(inflater, container, false).apply {
            signUpViewModel = viewModel

            lifecycleOwner = this@CheckSMSCodeFragment
        }

        setBottomBarVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyButton.setOnClickListener {
            viewModel.smsCheck()
        }
    }
}
