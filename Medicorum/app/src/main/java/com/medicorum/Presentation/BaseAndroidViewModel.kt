package com.medicorum.Presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseAndroidViewModel(
    application: Application
) : AndroidViewModel(application) {
    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}