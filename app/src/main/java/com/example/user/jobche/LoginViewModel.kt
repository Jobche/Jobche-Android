package com.example.user.jobche


import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.Bindable
import androidx.databinding.BaseObservable
import androidx.lifecycle.ViewModel
import com.example.user.jobche.Model.LoginUser
import com.google.android.material.snackbar.Snackbar
import androidx.core.os.HandlerCompat.postDelayed
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.UI.SignupPasswordActivity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        val paramObject = JsonObject()
        paramObject.addProperty("email", this.email)
        paramObject.addProperty("password", this.password)

        val call: Call<LoginUser> = RetrofitClient().getApi()
            .loginUser(paramObject)

        call.enqueue(object: Callback<LoginUser> {
            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                Log.d("Login user failure:", t.message.toString())
            }

            override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                val res:String = response.body().toString()
                Log.d("Login user Success:", res)

            }

        })
    }

}