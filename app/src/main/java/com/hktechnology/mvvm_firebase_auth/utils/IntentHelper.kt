package com.hktechnology.mvvm_firebase_auth.utils

import android.content.Intent
import com.hktechnology.mvvm_firebase_auth.AppApplication
import com.hktechnology.mvvm_firebase_auth.MainActivity
import com.hktechnology.mvvm_firebase_auth.ui.login.LoginActivity
import com.hktechnology.mvvm_firebase_auth.ui.signup.SignUpActivity

class IntentHelper{

    private val context = AppApplication.getAppContext()

    fun intentToMain() {
        var intent = Intent(context,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context
            .startActivity(intent)
    }

    fun intentToLogin() {
        var intent = Intent(context,LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context
            .startActivity(intent)
    }

    fun intentToSignUp() {
        var intent = Intent(context,SignUpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context
            .startActivity(intent)
    }
}