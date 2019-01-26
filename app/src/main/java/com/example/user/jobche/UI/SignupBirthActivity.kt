package com.example.user.jobche.UI


import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.R
import com.example.user.jobche.SignupBirthViewModel
import com.example.user.jobche.databinding.ActivitySignupBirthBinding
import java.util.*

class SignupBirthActivity : AppCompatActivity() {

    private lateinit var birthDate: DateOfBirth
    private  val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_birth)

        val binding: ActivitySignupBirthBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_birth)
        val signupBirthViewModel = SignupBirthViewModel()
        binding.viewModel = signupBirthViewModel
        signupBirthViewModel.setRegisterUser(intent.getParcelableExtra("RegisterUser"))

        signupBirthViewModel.birthDateEventLiveData.observe(this, Observer {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, yearCalendar, monthOfYear, dayOfMonth ->
                birthDate = DateOfBirth(dayOfMonth, monthOfYear+1, yearCalendar)
                signupBirthViewModel.setBirthDate(birthDate)
            }, year, month, day)
            dpd.show()
        })


        signupBirthViewModel.nextEventLiveData.observe(this, Observer {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

    }
}


