package com.example.user.jobche.Model

import android.text.TextUtils
import android.util.Patterns

class LoginUser() {

    private var email: String = ""
    private var password: String = ""

    val isDataValid: Boolean
        get() = (!TextUtils.isEmpty(getEmail()))
                && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()
                && getPassword().length > 6

    fun getEmail(): String {
        return email
    }

    fun getPassword(): String {
        return password
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }
}