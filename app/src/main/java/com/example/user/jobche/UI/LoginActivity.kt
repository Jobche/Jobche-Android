package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.user.jobche.LoginViewModel
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val loginViewModel = LoginViewModel()
        binding.viewModel = loginViewModel

        loginViewModel.signupEventLiveData.observe(this, Observer {
            startActivity(Intent(this, SignupNameActivity::class.java))
        })

        loginViewModel.loginEventLiveData.observe(this, Observer {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("Email", loginViewModel.getEmail())
            intent.putExtra("Password", loginViewModel.getPassword())
            startActivity(intent)
        })
    }
}