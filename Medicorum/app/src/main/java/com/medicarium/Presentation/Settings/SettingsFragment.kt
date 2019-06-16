package com.medicarium.Presentation.Settings

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.medicarium.Presentation.Services.DialogService
import com.medicarium.R
import kotlinx.android.synthetic.main.settings_fragment.*
import org.jetbrains.anko.support.v4.act
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SettingsFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: SettingsViewModel
    private val dialogService: DialogService by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        termTextView.setOnClickListener {
            dialogService.showNeutralDialog(
                context = activity!!,
                title = "Terms and Conditions",
                message = getString(R.string.terms),
                buttonText = getString(R.string.ok_text_caps),
                clickListener = DialogInterface.OnClickListener { _, _ -> }
            )
        }
    }
}
