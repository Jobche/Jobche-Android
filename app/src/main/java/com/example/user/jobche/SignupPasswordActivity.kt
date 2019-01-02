package com.example.user.jobche

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.user.jobche.Model.RegisterUser
import kotlinx.android.synthetic.main.activity_signup_password.*

class SignupPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_password)

        val mNext = findViewById<Button>(R.id.next_pass_btn)
        mNext.setOnClickListener {
//            val registerUser: RegisterUser = intent.getParcelableExtra("RegisterUser")
            val password = signup_password.text.toString()
            val intent = Intent(this, SignupBirthActivity::class.java)
            intent.putExtra("password", password)
            startActivity(intent)
        }
    }
}
