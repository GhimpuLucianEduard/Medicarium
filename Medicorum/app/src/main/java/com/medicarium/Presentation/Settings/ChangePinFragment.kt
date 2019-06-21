package com.medicarium.Presentation.Settings

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.Services.DialogService
import com.medicarium.R
import com.medicarium.databinding.ChangePinFragmentBinding
import kotlinx.android.synthetic.main.change_pin_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class ChangePinFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: ChangePinViewModel
    private lateinit var binding: ChangePinFragmentBinding
    private val viewModelFactory: ChangePinViewModelFactory by instance()
    private val dialogService: DialogService by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChangePinViewModel::class.java)

        binding = ChangePinFragmentBinding.inflate(inflater, container, false).apply {
            changePinViewModel = viewModel
            lifecycleOwner = this@ChangePinFragment
        }
        setBottomBarVisibility(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backTextView.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigateUp()
        }

        saveChangedButton.setOnClickListener {
            if (viewModel.pinCode.value!!.isEmpty() || viewModel.oldPin.value!!.isEmpty() || viewModel.confirmPinCode.value!!.isEmpty()) {
                dialogService.showNeutralDialog(
                    activity!!,
                    "Error",
                    "You must complete all the fields.",
                    "Ok", DialogInterface.OnClickListener { _, _ -> })
            } else {
                viewModel.onSaveClicked()
                dialogService.showNeutralDialog(
                    activity!!,
                    "Pin changed",
                    "You can now use the new PIN to acces the app.",
                    "Ok", DialogInterface.OnClickListener { _, _ ->
                        Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                            .navigateUp()
                    })
            }
        }
    }
}
