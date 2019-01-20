package com.example.user.jobche.UI


import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.user.jobche.R
import com.example.user.jobche.SignupBirthViewModel
import com.example.user.jobche.databinding.ActivitySignupBirthBinding

class SignupBirthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_birth)

        val binding: ActivitySignupBirthBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_birth)
        val signupBirthViewModel = SignupBirthViewModel()
        binding.viewModel = signupBirthViewModel
        signupBirthViewModel.setRegisterUser(intent.getParcelableExtra("RegisterUser"))

        signupBirthViewModel.nextEventLiveData.observe(this, Observer {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

    }
}


