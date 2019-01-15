package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.R
import com.example.user.jobche.RegisterApi
import com.example.user.jobche.RetrofitClient
import com.example.user.jobche.SignupBirthViewModel
import com.example.user.jobche.databinding.ActivitySignupBirthBinding
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_signup_birth.*
import kotlinx.android.synthetic.main.activity_signup_password.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

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


