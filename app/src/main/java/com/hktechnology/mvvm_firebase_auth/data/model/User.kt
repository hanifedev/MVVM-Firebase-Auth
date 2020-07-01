package com.hktechnology.mvvm_firebase_auth.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
class User : Parcelable {
    var email: String? = null
    var password: String? = null
    var name: String? = null
    var createdTime: String? = null
}