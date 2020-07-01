package com.hktechnology.mvvm_firebase_auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hktechnology.mvvm_firebase_auth.data.model.User
import com.hktechnology.mvvm_firebase_auth.utils.Consts.DatabaseConst.USERS
import io.reactivex.Completable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class UserRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    private val rootRef = FirebaseFirestore.getInstance()
    private val usersRef = rootRef.collection(USERS)

    fun login(username: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    } else
                        emitter.onError(it.exception!!)
                }
            }
    }

    fun signUp(user: User) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(user.email!!, user.password!!)
            .addOnSuccessListener {
                val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                user.createdTime = currentDate
                val uid = firebaseAuth.currentUser!!.uid
                usersRef.document(uid).set(user).addOnSuccessListener {
                    emitter.onComplete()
                }.addOnFailureListener {
                    emitter.onError(it)
                }
            }
    }

    fun userInfo(): FirebaseUser? {
        return firebaseAuth.currentUser
    }


}