package com.example.user.jobche.UI

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.user.jobche.Interface.LoginResultCallBacks
import com.example.user.jobche.R
import com.example.user.jobche.ViewModel.LoginViewModel
import com.example.user.jobche.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginResultCallBacks {
    override fun onSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }
}
