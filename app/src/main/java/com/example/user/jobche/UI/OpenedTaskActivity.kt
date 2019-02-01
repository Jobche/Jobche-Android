package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.user.jobche.Model.Task
import com.example.user.jobche.R
import com.example.user.jobche.OpenedTaskViewModel
import com.example.user.jobche.databinding.ActivityOpenedTaskBinding
import com.example.user.jobche.databinding.OpenedTaskInformationBinding

class OpenedTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opened_task)


        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        val email = sharedPreferences.getString("EMAIL", "")!!
        val password = sharedPreferences.getString("PASSWORD", "")!!


        val task:Task = intent.getParcelableExtra("Task")

        val binding: ActivityOpenedTaskBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_opened_task
        )

        val openedTaskViewModel = OpenedTaskViewModel(task)
        binding.viewModel = openedTaskViewModel
        binding.frameOpenedTask.viewModel = openedTaskViewModel

        openedTaskViewModel.setEmail(email)
        openedTaskViewModel.setPassword(password)

        openedTaskViewModel.onClickEventLiveData.observe(this, Observer {
            Toast.makeText(this, "You Applied Successfully", Toast.LENGTH_LONG).show()
        })


    }
}
