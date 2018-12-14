package com.example.user.jobche

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mLogin = findViewById<Button>(R.id.login_btn)
        mLogin.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val mSignup = findViewById<Button>(R.id.signup_btn)
        mSignup.setOnClickListener {
            val intent = Intent(this, SignupNameActivity::class.java)
            startActivity(intent)
        }
    }
}
