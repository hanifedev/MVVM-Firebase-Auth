package com.hktechnology.mvvm_firebase_auth.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hktechnology.mvvm_firebase_auth.R
import com.hktechnology.mvvm_firebase_auth.data.model.LoginFormState
import com.hktechnology.mvvm_firebase_auth.data.model.User
import com.hktechnology.mvvm_firebase_auth.data.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import com.hktechnology.mvvm_firebase_auth.data.model.Result
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _signUpResult = MutableLiveData<Result>()
    val signUpResult: LiveData<Result> = _signUpResult

    private val _signUpForm = MutableLiveData<LoginFormState>()
    val signUpFormState: LiveData<LoginFormState> = _signUpForm

    private var disposables = CompositeDisposable()

    fun postSignUpData(user: User) {
        disposables.add(userRepository.signUp(user).subscribeWith(
            object : DisposableCompletableObserver() {
                override fun onComplete() {
                    _signUpResult.value = Result(success = R.string.sign_up_success)
                }

                override fun onError(e: Throwable) {
                    _signUpResult.value = Result(error = R.string.sign_up_error)
                }
            }
        ))
    }

    fun formDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _signUpForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _signUpForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _signUpForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun clearDisposable()
    {
        disposables.clear()
    }

    private fun isEmailValid(email: String): Boolean {
        return '@' in email
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}