package com.hktechnology.mvvm_firebase_auth.data.model

data class LoginFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)