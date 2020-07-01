package com.hktechnology.mvvm_firebase_auth.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(private val splashViewModel: SplashViewModel) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return splashViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}