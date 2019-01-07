package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.user.jobche.BR.viewModel
import com.example.user.jobche.LoginViewModel
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_signup_name.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val loginViewModel = LoginViewModel()
        binding.viewModel = loginViewModel

        val mSignup = findViewById<Button>(R.id.signup_btn)
        mSignup.setOnClickListener {
            val intent = Intent(this, SignupNameActivity::class.java)
            startActivity(intent)
        }

    }
}