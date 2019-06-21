package com.medicarium.Presentation.Settings

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.Services.DialogService
import com.medicarium.R
import com.medicarium.Utilities.EventObserver
import kotlinx.android.synthetic.main.settings_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SettingsFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: SettingsViewModel
    private val dialogService: DialogService by instance()
    private val viewModelFactory: SettingsViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserPreferences()

        viewModel.navigateToLogin.observe(this@SettingsFragment, EventObserver {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())
        })

        emailTextView.text = viewModel.user.email

        termTextView.setOnClickListener {
            dialogService.showNeutralDialog(
                context = activity!!,
                title = "Terms and Conditions",
                message = getString(R.string.terms),
                buttonText = getString(R.string.ok_text_caps),
                clickListener = DialogInterface.OnClickListener { _, _ -> }
            )
        }

        logoutTextView.setOnClickListener {
            viewModel.signout()
        }

        viewModel.isEmergencyModeOn.observe(this@SettingsFragment, Observer {
            emergencyModeSwitch.isChecked = it
        })

        emergencyModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setPreferences(isChecked)
        }

        changePinTextView.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(SettingsFragmentDirections.actionSettingsFragmentToChangePinFragment())
        }
    }
}
