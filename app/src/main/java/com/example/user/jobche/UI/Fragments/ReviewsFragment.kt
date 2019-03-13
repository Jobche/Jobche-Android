package com.example.user.jobche.UI.Fragments

import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.user.jobche.Adapters.AppliersRecyclerViewAdapter
import com.example.user.jobche.Model.UserProfile

import com.example.user.jobche.R
import com.example.user.jobche.databinding.FragmentReviewsBinding
import com.example.user.jobche.databinding.FragmentTaskAppliedBinding


class ReviewsFragment : Fragment(), AppliersRecyclerViewAdapter.OnApplierClickListener {


    private lateinit var recyclerView: RecyclerView
    private var reviewWorkers = ArrayList<UserProfile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentReviewsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false)
//        val taskAppliersViewModel = TaskAcceptedViewModel(task, email, password)
        val bundle = arguments
        if (bundle != null) {
            reviewWorkers = bundle.getParcelableArrayList("ReviewWorkers")!!
        }

        recyclerView = binding.listOfWorkers
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = AppliersRecyclerViewAdapter(
            reviewWorkers,
            this
        )

        return binding.root
    }

    override fun onClick(position: Int) {
        Toast.makeText(activity, "Shte go reitvam toz" + position.toString(), Toast.LENGTH_SHORT).show()
    }
}
