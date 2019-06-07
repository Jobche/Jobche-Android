package com.example.user.jobche.UI.Fragments

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.Adapters.ReviewsRecyclerViewAdapter
import com.example.user.jobche.Model.Review
import com.example.user.jobche.databinding.FragmentReviewsHistoryBinding
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity


class ReviewsHistoryFragment : Fragment() {

    private lateinit var reviews: ArrayList<Review>
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Оценики"
            (activity as HomeActivity).showBackButton(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        if (bundle != null) {
            reviews = bundle.getParcelableArrayList("Reviews")!!
        }

        val binding: FragmentReviewsHistoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reviews_history, container, false)

        recyclerView = binding.listOfReviews
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = ReviewsRecyclerViewAdapter(
            reviews
        )

        return binding.root
    }
}
