package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.R
import com.example.user.jobche.RegisterApi
import com.example.user.jobche.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_signup_birth.*
import kotlinx.android.synthetic.main.activity_signup_password.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignupBirthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_birth)
        val mNext = findViewById<Button>(R.id.next_birth_btn)
        mNext.setOnClickListener {

            val registerUser: RegisterUser = intent.getParcelableExtra("RegisterUser")
            registerUser.email = signup_email.text.toString()

            val paramObject = JsonObject()
            paramObject.addProperty("firstName", registerUser.firstName)
            paramObject.addProperty("lastName", registerUser.lastName)
            paramObject.addProperty("email", registerUser.email)
            paramObject.addProperty("password", registerUser.password)

            val call: Call<RegisterUser> = RetrofitClient().getApi()
                .createUser(paramObject)

            call.enqueue(object: Callback<RegisterUser>{
                override fun onFailure(call: Call<RegisterUser>, t: Throwable) {
                    Toast.makeText(this@SignupBirthActivity, t.message.toString(), Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RegisterUser>, response: Response<RegisterUser>) {
                    val res:String = response.body().toString()
                    Toast.makeText(this@SignupBirthActivity, res, Toast.LENGTH_LONG).show()
                }

            })

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}


