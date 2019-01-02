package com.example.user.jobche

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.jobche.Model.RegisterUser
import kotlinx.android.synthetic.main.activity_signup_password.*

class SignupBirthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_birth)
        Toast.makeText(this,intent.getStringExtra("FirstName") , Toast.LENGTH_LONG).show()
        val mNext = findViewById<Button>(R.id.next_birth_btn)
        mNext.setOnClickListener {
            val firstname: String = intent.getStringExtra("FirstName")
            val lastName: String = intent.getStringExtra("LastName")
            val email = signup_password.text.toString()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
