package com.hktechnology.mvvm_firebase_auth.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.hktechnology.mvvm_firebase_auth.R
import com.hktechnology.mvvm_firebase_auth.data.model.Result
import com.hktechnology.mvvm_firebase_auth.data.model.User
import com.hktechnology.mvvm_firebase_auth.data.repository.SplashRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val splashRepository: SplashRepository) :
    ViewModel() {

    private var disposables = CompositeDisposable()

    private val _isUserLoggedInLiveData = MutableLiveData<FirebaseUser>()
    val isUserLoggedInLiveData: LiveData<FirebaseUser> = _isUserLoggedInLiveData

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userLiveData

    fun checkIfUserLoggedIn() {
        _isUserLoggedInLiveData.value = splashRepository.checkIfUserIsLoggedIn()
    }

    fun setUid(uid: String) {
        disposables.add(splashRepository.getUser(uid).subscribe(
            { user ->
                _userLiveData.value = user
            }, { e ->
                Log.d("UserErr", e.message)
            }
        ))
    }

    fun clearDisposable() {

    }

}