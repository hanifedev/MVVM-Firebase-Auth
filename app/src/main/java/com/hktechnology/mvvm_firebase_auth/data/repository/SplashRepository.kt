package com.hktechnology.mvvm_firebase_auth.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hktechnology.mvvm_firebase_auth.data.model.User
import com.hktechnology.mvvm_firebase_auth.utils.Consts.DatabaseConst.USERS
import io.reactivex.Single
import javax.inject.Inject

class SplashRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {



    fun checkIfUserIsLoggedIn(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun getUser(uid: String): Single<User> {
        val rootRef = FirebaseFirestore.getInstance()
        val usersRef = rootRef.collection(USERS)
        return Single.create { emitter ->
            usersRef.document(uid).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    var document = it.result
                    if (document!!.exists()) {
                        emitter.onSuccess(document.toObject(User::class.java)!!)
                    }
                } else emitter.onError(Exception(it.exception))
            }
        }
    }
}
