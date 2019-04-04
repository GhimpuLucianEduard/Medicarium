package com.medicarium.Presentation.Signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class CheckSmscodeViewModel : ViewModel() {

    val isBusy = MutableLiveData<Boolean>()
    val smsCode = MutableLiveData<String>()

    fun smsCheck() {
        isBusy.value = true
        Log.i("API_REQ", smsCode.value.toString())
    }
}
