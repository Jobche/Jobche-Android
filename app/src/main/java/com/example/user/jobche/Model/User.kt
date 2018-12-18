package com.example.user.jobche.Model

import android.databinding.BaseObservable
import android.text.TextUtils
import android.util.Patterns

class User(private var email: String, private var password: String): BaseObservable() {

    val isDataValid: Boolean

    get() = (!TextUtils.isEmpty(getEmail()))
            && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
            && getPassword().length > 6

    public fun getEmail(): String {
        return email
    }

    public fun getPassword(): String {
        return password
    }

    public fun setEmail(email: String) {
        this.email = email
    }

    public fun setPassoword(password: String) {
        this.password = password
    }
}