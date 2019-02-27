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
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentApplierProfileBinding

class ApplierProfileFragment : Fragment() {

    private lateinit var email:String
    private lateinit var password:String
    private var applicationId:Int = 0
    private var applierId:Int = 0
    private lateinit var name:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        val bundle = arguments
        if (bundle != null) {
            applicationId = bundle.getInt("ApplicationId")
            applierId = bundle.getInt("ApplierId")
            name = bundle.getString("Name")!!
        }

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Профил на $name"
            (activity as HomeActivity).showBackButton(true)
        }

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val binding: FragmentApplierProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_applier_profile, container, false)
        val profileViewModel = ProfileViewModel()
        profileViewModel.userId = (applierId)
        profileViewModel.applicationId = applicationId
        binding.viewModel = profileViewModel
        binding.profileInfo.viewModel = profileViewModel
        profileViewModel.email = email
        profileViewModel.password = password
        profileViewModel.getUser()

        return binding.root
    }
}
