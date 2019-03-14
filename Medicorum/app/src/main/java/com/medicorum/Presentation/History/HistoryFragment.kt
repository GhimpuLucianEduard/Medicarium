package com.medicorum.Presentation.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicorum.Presentation.BaseFragment
import com.medicorum.R


class HistoryFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setBottomBarVisibility(true)
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

}
