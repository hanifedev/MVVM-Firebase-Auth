package com.hktechnology.mvvm_firebase_auth.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SignUpViewModelFactory @Inject constructor(private val signUpViewModel: SignUpViewModel) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return signUpViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}