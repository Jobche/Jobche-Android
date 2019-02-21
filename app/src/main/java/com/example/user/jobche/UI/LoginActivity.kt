package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.user.jobche.LoginViewModel
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = LoginViewModel()
        binding.viewModel = loginViewModel
        saveData() //to reset sharedPreferences parameters

        loginViewModel.loginEventLiveData.observe(this, Observer {
            saveData() //to set sharedPreferences parameters
            Log.d("Otiam", "kym home")
            startActivity(Intent(this, HomeActivity::class.java))
        })

        loginViewModel.signupEventLiveData.observe(this, Observer {
            startActivity(Intent(this, SignupNameActivity::class.java))
        })

        loginViewModel.failEventLiveData.observe(this, Observer {
            Toast.makeText(this, loginViewModel.toastMsg, Toast.LENGTH_LONG).show()
        })
    }

    fun saveData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("EMAIL", loginViewModel.email)
        editor.putString("PASSWORD", loginViewModel.password)
        editor.putInt("ID", loginViewModel.id)
        editor.putBoolean("IS_LOGGED", loginViewModel.isLogged)
        editor.apply()
    }
}
