package com.hktechnology.mvvm_firebase_auth.di

import com.google.firebase.auth.FirebaseAuth
import com.hktechnology.mvvm_firebase_auth.data.repository.SplashRepository
import com.hktechnology.mvvm_firebase_auth.data.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideSplashRepository(firebaseAuth: FirebaseAuth): SplashRepository = SplashRepository(firebaseAuth)

    @Provides
    fun provideUserRepository(firebaseAuth: FirebaseAuth): UserRepository = UserRepository(firebaseAuth)

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


}