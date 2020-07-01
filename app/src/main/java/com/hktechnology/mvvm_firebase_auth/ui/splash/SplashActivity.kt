package com.hktechnology.mvvm_firebase_auth.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hktechnology.mvvm_firebase_auth.AppApplication
import com.hktechnology.mvvm_firebase_auth.MainActivity
import com.hktechnology.mvvm_firebase_auth.R
import com.hktechnology.mvvm_firebase_auth.data.model.User
import com.hktechnology.mvvm_firebase_auth.ui.login.LoginActivity
import com.hktechnology.mvvm_firebase_auth.utils.Consts.IntentConst.USER
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory
    private lateinit var splashViewModel: SplashViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AppApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel =
            ViewModelProvider(this@SplashActivity, splashViewModelFactory)
                .get(SplashViewModel::class.java)
        val timerThread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        timerThread.start()
        splashViewModel?.checkIfUserLoggedIn()
        splashViewModel?.isUserLoggedInLiveData?.observe(this, Observer { user ->
            when {
                user != null -> {
                    getUserFromDatabase(user!!.uid)
                }
                else -> {
                    goToLoginActivity()
                    finish()
                }
            }
        })
    }

    private fun goToLoginActivity() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getUserFromDatabase(uid: String) {
        splashViewModel?.setUid(uid)
        splashViewModel?.userLiveData?.observe(this, Observer {
            goToMainActivity(it)
        })
    }

    private fun goToMainActivity(user: User) {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.putExtra(USER, user)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        splashViewModel.clearDisposable()
        super.onDestroy()
    }
}