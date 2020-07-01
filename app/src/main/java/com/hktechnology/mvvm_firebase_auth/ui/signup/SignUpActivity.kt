package com.hktechnology.mvvm_firebase_auth.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.hktechnology.mvvm_firebase_auth.AppApplication
import com.hktechnology.mvvm_firebase_auth.R
import com.hktechnology.mvvm_firebase_auth.data.model.User
import com.hktechnology.mvvm_firebase_auth.ui.login.afterTextChanged
import com.hktechnology.mvvm_firebase_auth.utils.IntentHelper
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModelFactory
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var name: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AppApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        signUpViewModel = ViewModelProvider(this@SignUpActivity, signUpViewModelFactory)
            .get(SignUpViewModel::class.java)
        setContentView(R.layout.activity_sign_up)
        val signUp = findViewById<Button>(R.id.sign_up)
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        val loading = findViewById<ProgressBar>(R.id.loading)

        signUpViewModel.signUpFormState.observe(this@SignUpActivity, Observer {
            val loginState = it ?: return@Observer

            signUp.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                email.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        signUpViewModel.signUpResult.observe(this@SignUpActivity, Observer { result ->

            if (result.error != null) {
                showMessage(R.string.sign_up_error)
                loading.visibility = View.GONE
            }

            if (result.success != null) {
                showMessage(R.string.sign_up_success)
                IntentHelper().intentToLogin()
            }

        })

        email.afterTextChanged {
            signUpViewModel.formDataChanged(
                email.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                signUpViewModel.formDataChanged(
                    email.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                val user = User()
                user.email = email.text.toString()
                user.name = name.text.toString().capitalizeWords()
                user.password = password.text.toString()
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        signUpViewModel.postSignUpData(
                            user
                        )
                }
                false
            }

            signUp.setOnClickListener {
                loading.visibility = View.VISIBLE
                signUp()
            }
        }
    }

    private fun showMessage(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun signUp() {
        val user = User()
        user.email = email.text.toString()
        user.name = name.text.toString().capitalizeWords()
        user.password = password.text.toString()
        signUpViewModel.postSignUpData(
            user
        )
    }


    private fun String.capitalizeWords(): String =
        split(" ").map { it.toLowerCase().capitalize() }.joinToString(" ")


    override fun onDestroy() {
        signUpViewModel.clearDisposable()
        super.onDestroy()
    }
}