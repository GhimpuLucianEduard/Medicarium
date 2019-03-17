package com.medicorum.Presentation.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.medicorum.Presentation.BaseFragment
import com.medicorum.databinding.FragmentPinAuthBinding
import kotlinx.android.synthetic.main.fragment_pin_auth.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PinAuthFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: LoginFragmentViewModelFactory by instance()
    private lateinit var viewModel: LoginFragmentViewModel
    private lateinit var binding : FragmentPinAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginFragmentViewModel::class.java)
        binding = FragmentPinAuthBinding.inflate(inflater, container, false).apply {
            loginViewModel = viewModel
        }

        setBottomBarVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //pinLockView.attachIndicatorDots(indicatorDots)
    }

}
