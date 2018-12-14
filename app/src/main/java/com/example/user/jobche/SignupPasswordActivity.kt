package com.example.user.jobche

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignupPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_password)

        val mNext = findViewById<Button>(R.id.next_pass_btn)
        mNext.setOnClickListener {
            val intent = Intent(this, SignupBirthActivity::class.java)
            startActivity(intent)
        }
    }
}
