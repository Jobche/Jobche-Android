package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.user.jobche.LoginViewModel
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {


    var isLogged:Boolean = false

    private lateinit var loginViewModel:LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = LoginViewModel()
        binding.viewModel = loginViewModel

        loginViewModel.signupEventLiveData.observe(this, Observer {
            startActivity(Intent(this, SignupNameActivity::class.java))
        })

        loginViewModel.loginEventLiveData.observe(this, Observer {
            isLogged = true
            saveData()
            startActivity(Intent(this, HomeActivity::class.java))
        })
    }

    fun saveData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("EMAIL", loginViewModel.getEmail())
        editor.putString("PASSWORD", loginViewModel.getPassword())
        editor.putBoolean("IS_LOGGED", isLogged)
        editor.apply()
    }
}