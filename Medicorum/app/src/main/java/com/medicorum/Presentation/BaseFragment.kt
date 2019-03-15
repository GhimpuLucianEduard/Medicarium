package com.medicorum.Presentation

import androidx.fragment.app.Fragment
import com.medicorum.MedicorumApp
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun setBottomBarVisibility(bool: Boolean) {
        (activity as? MainActivity)!!.setBottomBarVisibility(bool)
    }

    protected fun showToast(msg: String, length: Int = 0) {
        (activity as? MainActivity)!!.showToast(msg, length)
    }

}