package com.example.user.jobche

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.user.jobche.Model.RegisterUser
import kotlinx.android.synthetic.main.activity_signup_name.*


class SignupNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_name)

        val mNext = findViewById<Button>(R.id.next_name_btn)
        mNext.setOnClickListener {
//            val registerUser:RegisterUser = RegisterUser()
            val firstname:String = signup_firstname.text.toString()
            val lastname = signup_lastname.text.toString()
            val intent = Intent(this, SignupPasswordActivity::class.java)
            intent.putExtra("FirstName", firstname).putExtra("Lastname", lastname)
            startActivity(intent)
        }
    }
}
