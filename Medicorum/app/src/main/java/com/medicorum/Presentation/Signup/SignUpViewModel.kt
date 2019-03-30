package com.medicorum.Presentation.Signup

import androidx.lifecycle.ViewModel
import com.medicorum.Data.ApiServices.AuthService

class SignUpViewModel(val authService: AuthService) : ViewModel() {

    var firstSignUpViewModel: FirstSignupViewModel = FirstSignupViewModel(authService)

}