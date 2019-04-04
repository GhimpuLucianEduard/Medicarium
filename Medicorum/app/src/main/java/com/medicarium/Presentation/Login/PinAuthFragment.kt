package com.medicarium.Presentation.Login

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.Services.VibrationService
import com.medicarium.R
import com.medicarium.databinding.FragmentPinAuthBinding
import empty
import kotlinx.android.synthetic.main.fragment_pin_auth.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PinAuthFragment : BaseFragment(), KodeinAware, View.OnClickListener {

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
        binding.lifecycleOwner = this

        setBottomBarVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // TODO: maybe refactor this (Recycler view?)
        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnThree.setOnClickListener(this)
        btnFour.setOnClickListener(this)
        btnFive.setOnClickListener(this)
        btnSix.setOnClickListener(this)
        btnSeven.setOnClickListener(this)
        btnEight.setOnClickListener(this)
        btnNine.setOnClickListener(this)
        btnZero.setOnClickListener(this)
        deleteFrame.setOnClickListener(this)
        btnFingerprint.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            deleteFrame -> clearDots()
            btnFingerprint -> navigateToFingerprintScreen()
            else -> onPinButtonClicked(view as Button)
        }
    }

    private fun navigateToFingerprintScreen() {
        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            .navigate(PinAuthFragmentDirections.actionPinAuthFragmentToFingerprintAuthFragment())
    }

    fun onPinButtonClicked(pinButton: Button) {

        viewModel.updatePin(pinButton.text)

        when (viewModel.pin.length) {
            1 -> (dotView1.background as TransitionDrawable).startTransition(300)
            2 -> (dotView2.background as TransitionDrawable).startTransition(300)
            3 -> (dotView3.background as TransitionDrawable).startTransition(300)
            4 -> {
                (dotView4.background as TransitionDrawable).startTransition(300)
                if (viewModel.isPinValid()) {
                    val action = PinAuthFragmentDirections.actionPinAuthFragmentToGenericInfoFragment()
                    Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                        .navigate(action)
                } else {
                    val vibrator: VibrationService by instance()
                    vibrator.vibrate(context!!, 300)
                    clearDots()
                    gridLayoutDots.startAnimation(AnimationUtils.loadAnimation(context!!, R.anim.shake))
                }
            }
        }
    }

    // TODO: not proud of this code
    fun clearDots() {


        when (viewModel.pin.length) {
            1 -> (dotView1.background as TransitionDrawable).reverseTransition(300)
            2 -> {
                (dotView1.background as TransitionDrawable).reverseTransition(300)
                (dotView2.background as TransitionDrawable).reverseTransition(300)
            }
            3 -> {
                (dotView1.background as TransitionDrawable).reverseTransition(300)
                (dotView2.background as TransitionDrawable).reverseTransition(300)
                (dotView3.background as TransitionDrawable).reverseTransition(300)
            }
            4 -> {

                (dotView1.background as TransitionDrawable).reverseTransition(300)
                (dotView2.background as TransitionDrawable).reverseTransition(300)
                (dotView3.background as TransitionDrawable).reverseTransition(300)
                (dotView4.background as TransitionDrawable).reverseTransition(300)
            }
        }

        viewModel.pin = String.empty()
    }
}
