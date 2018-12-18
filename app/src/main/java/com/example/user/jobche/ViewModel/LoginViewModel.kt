package com.example.user.jobche.ViewModel

import android.arch.lifecycle.ViewModel
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.user.jobche.Interface.LoginResultCallBacks
import com.example.user.jobche.Model.User

class LoginViewModel (private val listener:LoginResultCallBacks): ViewModel() {
    private val user: User = User("", "")

    val emailTextWatcher: TextWatcher
        get() = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun afterTextChanged(s: Editable?) {
                user.setEmail(s.toString())
            }

        }

    val passwordTextWatcher: TextWatcher
        get() = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun afterTextChanged(s: Editable?) {
                user.setPassoword(s.toString())
            }

        }

    fun onLoginClicked(v: View) {
        if(user.isDataValid) {
            listener.onSuccess("Login Success")
        }
        else {
            listener.onError("Login Failed")
        }

    }
}