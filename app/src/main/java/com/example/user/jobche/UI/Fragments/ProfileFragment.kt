package com.example.user.jobche.UI.Fragments

import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.ProfileViewModel
import com.example.user.jobche.R
import com.example.user.jobche.databinding.ActivityProfileBinding



class ProfileFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

//        val bundle = this.arguments

//        var id = bundle!!.getInt("Id", 0)
//        if (id == 0) {
           val id = sharedPreferences.getInt("ID", 0)
//        }

        val email = sharedPreferences.getString("EMAIL", "")!!
        val password = sharedPreferences.getString("PASSWORD", "")!!

        val binding: ActivityProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.activity_profile, container, false)
        val view: View = binding.root
        val profileViewModel = ProfileViewModel(id)
        binding.viewModel = profileViewModel

        profileViewModel.setEmail(email)
        profileViewModel.setPassword(password)
        profileViewModel.getUser()
        return view
    }
}
