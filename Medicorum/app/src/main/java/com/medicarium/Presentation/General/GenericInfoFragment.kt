package com.medicarium.Presentation.General


import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicarium.Presentation.BaseFragment
import com.medicarium.R
import kotlinx.android.synthetic.main.fragment_generic_info.*

class GenericInfoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setBottomBarVisibility(true)
        return inflater.inflate(R.layout.fragment_generic_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navBar.setLeftImageDrawnable(resources.getDrawable(R.drawable.empty_drawable))
        navBar.setRightImageDrawnable(resources.getDrawable(R.drawable.more_verti))
        navBar.setTitletext("")

        setupImageSwitchers()
    }

    private fun setupImageSwitchers() {

        firstNameImageSwitcher.setOnClickListener {
            (it.background as TransitionDrawable).reverseTransition(0)
        }
    }
}
