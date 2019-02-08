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
import com.example.user.jobche.databinding.FragmentApplierProfileBinding

class ApplierProfileFragment : Fragment() {

    private lateinit var email:String
    private lateinit var password:String
    private var userId:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        val bundle = arguments
        if (bundle != null) {
            userId = bundle.getInt("UserId")
        }

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val binding: FragmentApplierProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_applier_profile, container, false)
        val profileViewModel = ProfileViewModel(userId)
        binding.viewModel = profileViewModel
        binding.profileInfo.viewModel = profileViewModel
        profileViewModel.setEmail(email)
        profileViewModel.setPassword(password)
        profileViewModel.getUser()

        return binding.root
    }


}
