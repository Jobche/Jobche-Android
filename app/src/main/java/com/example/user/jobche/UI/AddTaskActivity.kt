package com.example.user.jobche.UI

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.user.jobche.AddTaskViewModel
import com.example.user.jobche.Model.DateParameters
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityAddTaskBinding
import kotlinx.android.synthetic.main.toolbar.*
import net.danlew.android.joda.JodaTimeAndroid
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        setContentView(R.layout.activity_add_task)
        toolbar_title.text="Add Task"

        val binding: ActivityAddTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        val addTaskViewModel = AddTaskViewModel()
        binding.viewModel = addTaskViewModel
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val dateParameters = DateParameters()


        addTaskViewModel.dateEventLiveData.observe(this, Observer {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, yearCalendar, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                dateParameters.yearCalendar = yearCalendar
                dateParameters.monthOfYear = monthOfYear
                dateParameters.dayOfMonth = dayOfMonth
                addTaskViewModel.setDate(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + yearCalendar)
            }, year, month, day)
            dpd.show()
        })

        addTaskViewModel.timeEventLiveData.observe(this, Observer {
            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->

                dateParameters.hour = hourOfDay
                dateParameters.minute = minuteOfHour
                val datetime = org.joda.time.LocalDateTime(dateParameters.yearCalendar, dateParameters.monthOfYear + 1, dateParameters.dayOfMonth, dateParameters.hour ,dateParameters.minute)
                addTaskViewModel.setDateTime(datetime)
                Log.d("DATATATAA KOPELE", datetime.toString())

                addTaskViewModel.setTime(hourOfDay.toString() + ":" + minuteOfHour)
            }, hour, minute, false)
            tpd.show()
                    })

        addTaskViewModel.addTaskEventLiveData.observe(this, Observer {
            startActivity(Intent(this, HomeActivity::class.java))
        })
    }
}

