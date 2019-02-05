package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.user.jobche.AddTaskViewModel
import com.example.user.jobche.databinding.ActivityAddTaskBinding
import net.danlew.android.joda.JodaTimeAndroid
import java.util.*
import com.example.user.jobche.R


class AddTaskActivity : AppCompatActivity() {

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private val hour = c.get(Calendar.HOUR_OF_DAY)
    private val minute = c.get(Calendar.MINUTE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        setContentView(R.layout.activity_add_task)

        val sharedPreferences: SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        val email = sharedPreferences.getString("EMAIL", "")!!
        val password = sharedPreferences.getString("PASSWORD", "")!!

        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(toolbar)


        val binding: ActivityAddTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        val addTaskViewModel = AddTaskViewModel()
        binding.viewModel = addTaskViewModel

        addTaskViewModel.setEmail(email)
        addTaskViewModel.setPassword(password)

        addTaskViewModel.dateEventLiveData.observe(this, Observer {

            val dpd =
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, yearCalendar, monthOfYear, dayOfMonth ->
                    // Display Selected date in textbox
                    addTaskViewModel.setYear(yearCalendar)
                    addTaskViewModel.setMonth(monthOfYear + 1)
                    addTaskViewModel.setDay(dayOfMonth)

                    addTaskViewModel.setDate(
                        String.format("%02d", dayOfMonth) + "-" + String.format("%02d",(monthOfYear + 1)) + "-" + yearCalendar)

                }, year, month, day)
            dpd.show()
        })

        addTaskViewModel.timeEventLiveData.observe(this, Observer {
            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->

                addTaskViewModel.setHour(hourOfDay)
                addTaskViewModel.setMinute(minuteOfHour)

                addTaskViewModel.setTime(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minuteOfHour))
            }, hour, minute, false)
            tpd.show()
        })

        addTaskViewModel.addTaskEventLiveData.observe(this, Observer {
            startActivity(Intent(this, HomeActivity::class.java))
        })
    }
}

