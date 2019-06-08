package com.medicarium.Presentation

import androidx.fragment.app.Fragment
import com.andreacioccarelli.cryptoprefs.CryptoPrefs
import com.medicarium.Utilities.SharedPreferences
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