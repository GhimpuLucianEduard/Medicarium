package com.medicarium.Presentation.Pin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.andreacioccarelli.cryptoprefs.CryptoPrefs
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.Services.DialogService
import com.medicarium.R
import com.medicarium.Utilities.EventObserver
import com.medicarium.Utilities.SharedPreferences
import com.medicarium.databinding.SetupPinFragmentBinding
import empty
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.setup_pin_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SetupPinFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SetupPinViewModelFactory by instance()
    private val dialogService: DialogService by instance()
    private lateinit var viewModel: SetupPinViewModel
    private lateinit var binding: SetupPinFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SetupPinViewModel::class.java)

        binding = SetupPinFragmentBinding.inflate(inflater, container, false).apply {
            setupPinViewModel = viewModel

            lifecycleOwner = this@SetupPinFragment
        }

        setBottomBarVisibility(false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        continueButton.setOnClickListener {
            handleContinueClicked()
        }

    }

    private fun handleContinueClicked() {
        if (viewModel.pinCode.value == viewModel.confirmPinCode.value) {
            dialogService.showNeutralDialog(
                activity!!,
                "Pin configured",
                "You can now ue the PIN to acces the app.",
                "Ok", DialogInterface.OnClickListener { _, _ ->
                    Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                        .navigate(SetupPinFragmentDirections.actionSetupPinFragmentToPinAuthFragment())
                })
            viewModel.onPinConfigured()
        } else {
            dialogService.showNeutralDialog(
                activity!!,
                "Error",
                "The PINs do not match, please check try again",
                "Ok", DialogInterface.OnClickListener { _, _ -> })
            viewModel.pinCode.value = String.empty()
            viewModel.confirmPinCode.value = String.empty()
        }
    }

}
