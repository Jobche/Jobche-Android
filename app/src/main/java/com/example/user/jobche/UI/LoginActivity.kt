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

        val loginViewModel = ViewModelProviders.of(this)
            .get(LoginViewModel::class.java)

        DataBindingUtil.setContentView<ActivityLoginBinding>(
            this, R.layout.activity_login
        ).apply { this.setLifecycleOwner(this@LoginActivity)
            this.viewModel = loginViewModel
        }

//        val activityLoginBinding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,
//            R.layout.activity_login
//        )
//        activityLoginBinding.viewModel = ViewModelProviders.of(this, LoginViewModelFactory(this))
//            .get(LoginViewModel::class.java)
    }
}
