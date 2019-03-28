package com.medicorum.Presentation.Signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.medicorum.Data.Models.User

class FirstSignupViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()

    val user : LiveData<User>
        get() = _user

    init {
        _user.value = User()
        _user.value?.id = "fsdaf"
    }
}
