package com.hktechnology.mvvm_firebase_auth

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.hktechnology.mvvm_firebase_auth.di.AppComponent
import com.hktechnology.mvvm_firebase_auth.di.DaggerAppComponent


class AppApplication : MultiDexApplication() {

    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }

    companion object {
        private lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}