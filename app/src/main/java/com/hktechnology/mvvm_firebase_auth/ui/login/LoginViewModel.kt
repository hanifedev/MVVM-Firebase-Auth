package com.hktechnology.mvvm_firebase_auth.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hktechnology.mvvm_firebase_auth.R
import com.hktechnology.mvvm_firebase_auth.data.model.LoginFormState
import com.hktechnology.mvvm_firebase_auth.data.repository.UserRepository
import com.hktechnology.mvvm_firebase_auth.data.model.Result
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Result>()
    val loginResult: LiveData<Result> = _loginResult

    private var disposables = CompositeDisposable()


    fun login(username: String, password: String) {
        disposables.add(userRepository.login(username, password)
            .subscribeWith(
                object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        _loginResult.value = Result(success = R.string.login_success)
                    }

                    override fun onError(e: Throwable) {
                        _loginResult.value = Result(error = R.string.login_failed)
                    }
                }
            )
        )
    }

    fun userInfo() {
        userRepository.userInfo()
    }

    fun loginDataChanged(password: String) {
        if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value =
                LoginFormState(isDataValid = true)
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}