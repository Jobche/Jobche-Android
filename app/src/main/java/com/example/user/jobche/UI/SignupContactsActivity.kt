package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.user.jobche.R
import com.example.user.jobche.RegisterUser
import com.example.user.jobche.SignupContactsViewModel
import com.example.user.jobche.databinding.ActivitySignupContactsBinding

class SignupContactsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_contacts)

        val user: RegisterUser = intent.getParcelableExtra("user")
        val binding: ActivitySignupContactsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup_contacts)
        binding.user = user
        val signupContactsViewModel = SignupContactsViewModel(user)
        binding.viewModel = signupContactsViewModel


        signupContactsViewModel.toastEventLiveData.observe(this, Observer {
            Toast.makeText(this, signupContactsViewModel.getToastMsg(), Toast.LENGTH_LONG).show()
        })

        signupContactsViewModel.nextEventLiveData.observe(this, Observer {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

    }
}
