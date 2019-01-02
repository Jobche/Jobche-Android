package com.example.user.jobche.ViewModel

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.user.jobche.Interface.LoginResultCallBacks
import com.example.user.jobche.Model.LoginUser

class LoginViewModel: ViewModel() {
    private val loginUser: LoginUser = LoginUser()

    val emailTextWatcher: TextWatcher
        get() = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                loginUser.setEmail(s.toString())
            }

        }

    val passwordTextWatcher: TextWatcher
        get() = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                loginUser.setPassword(s.toString())
            }

        }

//    fun onLoginClicked(v: View) {
//        if(loginUser.isDataValid) {
//            listener.onSuccess("Login Success")
//        }
//        else {
//            listener.onError("Login Failed")
//        }
//
//    }
}