package com.example.user.jobche

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignupNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_name)

        val mNext = findViewById<Button>(R.id.next_name_btn)
        mNext.setOnClickListener {
            val intent = Intent(this, SignupPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}
