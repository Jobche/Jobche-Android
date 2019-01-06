package com.example.user.jobche


import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.BaseObservable
import androidx.lifecycle.ViewModel
import com.example.user.jobche.Model.LoginUser
import com.google.android.material.snackbar.Snackbar
import androidx.core.os.HandlerCompat.postDelayed



class LoginViewModel: BaseObservable() {

    private var email:String = ""
    private var password:String = ""

    @Bindable
    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email:String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    @Bindable
    fun getPassword(): String {
        return this.password
    }

    fun setPassword(password:String) {
        this.password = password
        notifyPropertyChanged(BR.password)
    }

    fun onClick() {
        val email = this.email
        val password = this.password
        Log.d("OI YULI YULI YUILI", email)
        Log.d("OI YULI YULI YUILI", password)

    }

//    val emailTextWatcher: TextWatcher
//        get() = object: TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                loginUser.setEmail(s.toString())
//            }
//
//        }
//
//    val passwordTextWatcher: TextWatcher
//        get() = object: TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                loginUser.setPassword(s.toString())
//            }
//
//        }

}