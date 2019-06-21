package com.medicarium.Presentation

import androidx.fragment.app.Fragment
import com.andreacioccarelli.cryptoprefs.CryptoPrefs
import com.medicarium.Utilities.SharedPreferences
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment : Fragment() {

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected var shouldUseConnectivityOverlay: Boolean = false;

    protected fun setBottomBarVisibility(bool: Boolean) {
        (activity as? MainActivity)!!.setBottomBarVisibility(bool)
    }

    protected fun setProgressBarVisibility(visibility: Int) {
        (activity as? MainActivity)!!.progressBar.visibility = visibility
    }

    protected fun shouldUseConnectivityOverlay(bool: Boolean) {
        (activity as? MainActivity)!!.shouldDisplayNoConnectionOverlay = bool
        shouldUseConnectivityOverlay = bool
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)!!.refreshConnectivity(shouldUseConnectivityOverlay)
    }
}