package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.R
import kotlinx.android.synthetic.main.activity_signup_name.*


class SignupNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_name)

        val mNext = findViewById<Button>(R.id.next_name_btn)
        mNext.setOnClickListener {
            val registerUser: RegisterUser = RegisterUser()
            registerUser.firstName = signup_firstname.text.toString()
            registerUser.lastName = signup_lastname.text.toString()
            val intent = Intent(this, SignupPasswordActivity::class.java)
            intent.putExtra("RegisterUser", registerUser)
            Toast.makeText(this@SignupNameActivity, registerUser.lastName, Toast.LENGTH_LONG).show()

            startActivity(intent)
        }
    }
}
