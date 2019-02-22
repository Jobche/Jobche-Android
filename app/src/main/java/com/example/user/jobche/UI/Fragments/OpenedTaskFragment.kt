package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.user.jobche.Task
import com.example.user.jobche.R
import com.example.user.jobche.OpenedTaskViewModel
import com.example.user.jobche.ProfileViewModel
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentOpenedTaskBinding

class OpenedTaskFragment : Fragment() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!


        val bundle = this.arguments
        if (bundle != null) {
            task = bundle.getParcelable("Task")!!
        }


        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = ""
            (activity as HomeActivity).showBackButton(true)
        }

        val binding: FragmentOpenedTaskBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_opened_task, container, false)

        val openedTaskViewModel = OpenedTaskViewModel(task)
        val profileViewModel = ProfileViewModel()
        profileViewModel.setUserId(task.creatorId)
        binding.viewModel = openedTaskViewModel
        binding.frameOpenedTask.viewModel = openedTaskViewModel
        binding.frameOpenedTask.task = task
        binding.creatorInfo.viewModel = profileViewModel

        openedTaskViewModel.setEmail(email)
        openedTaskViewModel.setPassword(password)

        profileViewModel.setEmail(email)
        profileViewModel.setPassword(password)
        profileViewModel.getUser()

        openedTaskViewModel.onClickEventLiveData.observe(this, Observer {
            Toast.makeText(activity, "You Applied Successfully", Toast.LENGTH_LONG).show()
        })

        return binding.root
    }
}
