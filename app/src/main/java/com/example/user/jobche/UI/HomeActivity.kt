package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.user.jobche.HomeViewModel
import com.example.user.jobche.LoginViewModel
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel

        homeViewModel.fabEventLiveData.observe(this, Observer {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        })
    }
}
