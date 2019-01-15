package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.R
import com.example.user.jobche.SignupPasswordViewModel
import com.example.user.jobche.databinding.ActivitySignupPasswordBinding

class SignupPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_password)

        val binding: ActivitySignupPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_password)
        val signupPasswordViewModel = SignupPasswordViewModel()
        binding.viewModel = signupPasswordViewModel
        signupPasswordViewModel.setRegisterUser(intent.getParcelableExtra("RegisterUser"))

        signupPasswordViewModel.nextEventLiveData.observe(this, Observer {
            val intent = Intent(this, SignupBirthActivity::class.java)
            intent.putExtra("RegisterUser", signupPasswordViewModel.getRegisterUser())
            startActivity(intent)
        })
        signupPasswordViewModel.toastEventLiveData.observe(this, Observer {
            Toast.makeText(this, signupPasswordViewModel.getToastMsg(), Toast.LENGTH_SHORT).show()
        })



    }
}
