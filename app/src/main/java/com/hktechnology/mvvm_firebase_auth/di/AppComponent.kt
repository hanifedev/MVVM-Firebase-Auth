package com.hktechnology.mvvm_firebase_auth.di

import android.content.Context
import com.hktechnology.mvvm_firebase_auth.ui.login.LoginActivity
import com.hktechnology.mvvm_firebase_auth.ui.signup.SignUpActivity
import com.hktechnology.mvvm_firebase_auth.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: SplashActivity)

    fun inject(activity: SignUpActivity)

    fun inject(activity: LoginActivity)

}