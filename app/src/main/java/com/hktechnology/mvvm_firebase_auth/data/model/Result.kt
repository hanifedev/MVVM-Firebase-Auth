package com.hktechnology.mvvm_firebase_auth.data.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
/**
 * Authentication result : success (user details) or error message.
 */
data class Result(
    val success: Int? = null,
    val error: Int? = null
)