package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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


    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        saveData()//to reset SharedProPreferences

        viewModel.loginEventLiveData.observe(this, Observer {
            saveData()//to set SharedProPreferences
            startActivity(Intent(this, HomeActivity::class.java))
        })

        viewModel.signUpEventLiveData.observe(this, Observer {
            startActivity(Intent(this, SignupNameActivity::class.java))
        })

        viewModel.failEventLiveData.observe(this, Observer {
            Toast.makeText(this, viewModel.toastMsg, Toast.LENGTH_LONG).show()
        })
    }

    private fun saveData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("EMAIL", viewModel.user.email)
        editor.putString("PASSWORD", viewModel.user.password)
        editor.putInt("ID", viewModel.user.id.toInt())
        editor.putBoolean("IS_LOGGED", viewModel.isLogged)
        editor.apply()
    }
}
