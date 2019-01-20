package com.example.user.jobche.UI

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.user.jobche.Model.Task
import com.example.user.jobche.R
import com.example.user.jobche.OpenedTaskViewModel
import com.example.user.jobche.databinding.ActivityOpenedTaskBinding

class OpenedTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opened_task)

        val task:Task = intent.getParcelableExtra("Task")

        val binding: ActivityOpenedTaskBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_opened_task
        )
        val openedTaskViewModel = OpenedTaskViewModel(task)
        binding.viewModel = openedTaskViewModel




    }
}
