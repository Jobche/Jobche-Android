package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.jobche.*
import com.example.user.jobche.Model.Task
import com.example.user.jobche.databinding.ActivityHomeBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel
        val recyclerView = findViewById<RecyclerView>(R.id.listOfTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        homeViewModel.generateTasks()

        homeViewModel.adapterEventData.observe(this, Observer {
        recyclerView.adapter = RecyclerViewAdapter(homeViewModel.getTitles(),
                                                    homeViewModel.getLocations(),
                                                    homeViewModel.getDate(),
                                                    homeViewModel.getTime(),
                                                    homeViewModel.getPayments(),
                                                    homeViewModel.getNumberOfWorkers())
        })


        homeViewModel.fabEventLiveData.observe(this, Observer {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        })
    }
}
