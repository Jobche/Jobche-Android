package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.user.jobche.R
import com.example.user.jobche.RegisterUser
import com.example.user.jobche.SignupNameViewModel
import com.example.user.jobche.databinding.ActivitySignupNameBinding


class SignupNameActivity : AppCompatActivity() {

       private val user = RegisterUser()
       private val signupNameViewModel = SignupNameViewModel(user)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_name)
        val binding: ActivitySignupNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_name)
        binding.user = user
        binding.viewModel = signupNameViewModel

        signupNameViewModel.nextEventLiveData.observe(this, Observer {
            val intent = Intent(this, SignupPasswordActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        })

        signupNameViewModel.toastEventLiveData.observe(this, Observer {
            Toast.makeText(this, signupNameViewModel.getToastMsg(), Toast.LENGTH_LONG).show()
        })


    }
}
