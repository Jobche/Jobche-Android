package com.example.user.jobche.UI

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.user.jobche.AddTaskViewModel
import com.example.user.jobche.HomeViewModel
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityAddTaskBinding
import com.example.user.jobche.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.toolbar.*
import java.time.Month
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        toolbar_title.text="Add Task"

        val binding: ActivityAddTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        val addTaskViewModel = AddTaskViewModel()
        binding.viewModel = addTaskViewModel

        addTaskViewModel.dateEventLiveData.observe(this, Observer {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                addTaskViewModel.setDate(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year)
            }, year, month, day)
            dpd.show()
        })

        addTaskViewModel.timeEventLiveData.observe(this, Observer {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                addTaskViewModel.setTime(hourOfDay.toString() + ":" + minute)

            }, hour, minute, false)
            tpd.show()
        })

        addTaskViewModel.addTaskEventLiveData.observe(this, Observer {
            startActivity(Intent(this, HomeActivity::class.java))
        })
    }
}

