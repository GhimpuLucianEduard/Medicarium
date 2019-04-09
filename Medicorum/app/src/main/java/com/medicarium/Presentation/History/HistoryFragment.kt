package com.medicarium.Presentation.History

import addScaleAnimation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.medicarium.Presentation.BaseFragment
import com.medicarium.R
import kotlinx.android.synthetic.main.fragment_history.*


class HistoryFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setBottomBarVisibility(true)

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicalTestsImageButton.addScaleAnimation()
        drugsImageButton.addScaleAnimation()

        medicalTestsImageButton.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(HistoryFragmentDirections.actionHistoryFragmentToMedicalTestsFragment())
        }
    }
}
