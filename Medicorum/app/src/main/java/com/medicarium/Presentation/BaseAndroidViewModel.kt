package com.medicarium.Presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.andreacioccarelli.cryptoprefs.CryptoPrefs
import com.medicarium.Utilities.SharedPreferences
import io.reactivex.disposables.CompositeDisposable

abstract class BaseAndroidViewModel(
    application: Application
) : AndroidViewModel(application) {
    protected val compositeDisposable = CompositeDisposable()
    protected val preferences = CryptoPrefs(application, SharedPreferences.FILE_NAME, SharedPreferences.SECRET_KEY)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}