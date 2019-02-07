package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.user.jobche.UI.RecylclerViewAdapters.AppliersRecyclerViewAdapter
import com.example.user.jobche.TaskAppliersViewModel
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityTaskAppliersBinding

class TaskAppliersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_appliers)

        val sharedPreferences: SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val taskId:Int = intent.getIntExtra("TaskId", 0)
        Log.d("IDTASK", taskId.toString())

        val binding: ActivityTaskAppliersBinding = DataBindingUtil.setContentView(this, R.layout.activity_task_appliers)
        val taskAppliersViewModel = TaskAppliersViewModel()
        binding.viewModel = taskAppliersViewModel

        taskAppliersViewModel.setEmail(email)
        taskAppliersViewModel.setPassword(password)
        taskAppliersViewModel.setTaskId(taskId)

        recyclerView = binding.listOfAppliers
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        taskAppliersViewModel.getTaskAppliers()

        taskAppliersViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = AppliersRecyclerViewAdapter(
//                this,
                taskAppliersViewModel.getAppliers()
            )
        })


        taskAppliersViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    taskAppliersViewModel.setPage(page)
                    taskAppliersViewModel.getAppliers()
                }
            }
        })
    }

}
