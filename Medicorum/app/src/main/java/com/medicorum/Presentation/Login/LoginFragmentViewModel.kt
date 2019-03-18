package com.medicorum.Presentation.Login

import androidx.lifecycle.ViewModel
import empty

class LoginFragmentViewModel : ViewModel() {

    var pin: String = String.empty()

    fun updatePin(text: CharSequence?) {
        pin += text.toString()
    }

    fun isPinValid(): Boolean {
        return pin == "0203"
    }
}