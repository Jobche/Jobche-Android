package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.user.jobche.R
import com.example.user.jobche.RegisterUser
import com.example.user.jobche.SignupPasswordViewModel
import com.example.user.jobche.databinding.ActivitySignupPasswordBinding

class SignupPasswordActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_password)

        val user: RegisterUser = intent.getParcelableExtra("user")
        val binding: ActivitySignupPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_password)
        val signupPasswordViewModel = SignupPasswordViewModel(user)
        binding.user = user
        binding.viewModel = signupPasswordViewModel

        signupPasswordViewModel.nextEventLiveData.observe(this, Observer {
            val intent = Intent(this, SignupBirthActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        })

        signupPasswordViewModel.toastEventLiveData.observe(this, Observer {
            Toast.makeText(this, signupPasswordViewModel.toastMsg, Toast.LENGTH_LONG).show()
        })

    }
}
