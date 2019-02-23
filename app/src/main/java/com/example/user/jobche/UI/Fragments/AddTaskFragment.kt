package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.user.jobche.AddTask
import com.example.user.jobche.AddTaskViewModel
import com.example.user.jobche.Task
import net.danlew.android.joda.JodaTimeAndroid
import java.util.*
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentAddTaskBinding
import org.joda.time.LocalDate
import org.joda.time.LocalTime


class AddTaskFragment : Fragment() {

    private lateinit var email: String
    private lateinit var password: String

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private val hour = c.get(Calendar.HOUR_OF_DAY)
    private val minute = c.get(Calendar.MINUTE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(activity) // initialize the library before using it

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Добавяни Обява"
            (activity as HomeActivity).showBackButton(true)

        }

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val task = AddTask()
        val binding: FragmentAddTaskBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false)
        val addTaskViewModel = AddTaskViewModel(task, email, password)
        binding.viewModel = addTaskViewModel
        binding.task = task


        addTaskViewModel.dateEventLiveData.observe(this, Observer {

            val dpd =
                DatePickerDialog(
                    activity!!,
                    DatePickerDialog.OnDateSetListener { _, yearCalendar, monthOfYear, dayOfMonth ->
                        addTaskViewModel.localDate = (LocalDate(yearCalendar, monthOfYear + 1, dayOfMonth))
                    },
                    year,
                    month,
                    day
                )
            dpd.show()
        })

        addTaskViewModel.timeEventLiveData.observe(this, Observer {
            val tpd = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->
                addTaskViewModel.localTime = (LocalTime(hourOfDay, minuteOfHour))

            }, hour, minute, true)
            tpd.show()
        })

        addTaskViewModel.addTaskEventLiveData.observe(this, Observer {
            startActivity(Intent(activity, HomeActivity::class.java))
        })

        return binding.root
    }
}

