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
import com.medicarium.Utilities.EventObserver
import com.medicarium.databinding.CheckSmscodeFragmentBinding
import kotlinx.android.synthetic.main.check_smscode_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CheckSmsCodeFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SignUpViewModelFactory by instance()
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding : CheckSmscodeFragmentBinding

    companion object {
        fun newInstance() = CheckSmsCodeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding = CheckSmscodeFragmentBinding.inflate(inflater, container, false).apply {
            signUpViewModel = viewModel
            lifecycleOwner = this@CheckSmsCodeFragment

        }

        setBottomBarVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verifyButton.setOnClickListener {
            viewModel.smsCheck()
        }

        viewModel.isBusy.observe(this@CheckSmsCodeFragment, Observer {
            if (it)
                setProgressBarVisibility(View.VISIBLE)
            else
                setProgressBarVisibility(View.GONE)
        })

        viewModel.navigateToMainScreen.observe(this@CheckSmsCodeFragment, EventObserver {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(CheckSmsCodeFragmentDirections.actionCheckSMSCodeFragmentToGenericInfoFragment())
        })
    }
}
