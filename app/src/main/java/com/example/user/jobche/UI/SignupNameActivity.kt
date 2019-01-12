package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.user.jobche.LoginViewModel
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.R
import com.example.user.jobche.SignupNameViewModel
import com.example.user.jobche.databinding.ActivityLoginBinding
import com.example.user.jobche.databinding.ActivitySignupNameBinding
import kotlinx.android.synthetic.main.activity_signup_name.*


class SignupNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_name)

        val binding: ActivitySignupNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_name)
        val signupNameViewModel = SignupNameViewModel()
        binding.viewModel = signupNameViewModel

        signupNameViewModel.nextEventLiveData.observe(this, Observer {
            val intent = Intent(this, SignupPasswordActivity::class.java)
            intent.putExtra("RegisterUser", signupNameViewModel.getRegisterUser())
            startActivity(intent)
        })


    }
}
