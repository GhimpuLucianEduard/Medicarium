package com.medicorum.Presentation

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment : Fragment() {

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun setBottomBarVisibility(bool: Boolean) {
        (activity as? MainActivity)!!.setBottomBarVisibility(bool)
    }

    protected fun setProgressBarVisibility(visibility: Int) {
        (activity as? MainActivity)!!.progressBar.visibility = visibility
    }
}