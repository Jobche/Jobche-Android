package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.user.jobche.FilterViewModel
import com.example.user.jobche.Filter
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentFilterBinding
import org.joda.time.DateTime
import java.util.*


class FilterFragment : Fragment() {

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private val bundle:Bundle = Bundle()
    private lateinit var newFragment:Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Филтри"
            (activity as HomeActivity).showBackButton(true)
        }

        val filter = Filter()
        val binding: FragmentFilterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false)
        val filterViewModel = FilterViewModel(filter)
        binding.viewModel = filterViewModel
        binding.filter = filter


        filterViewModel.beginDateEventLiveData.observe(activity!!, Observer {
            val dpd = DatePickerDialog(
                activity!!,
                DatePickerDialog.OnDateSetListener { _, yearCalendar, monthOfYear, dayOfMonth ->
                    filterViewModel.dateStart = DateTime(yearCalendar, monthOfYear + 1, dayOfMonth, 0, 0)
                },
                year,
                month,
                day
            )
            dpd.show()
        })

        filterViewModel.endDateEventLiveData.observe(activity!!, Observer {
            val dpd = DatePickerDialog(
                activity!!,
                DatePickerDialog.OnDateSetListener { _, yearCalendar, monthOfYear, dayOfMonth ->
                    filterViewModel.dateEnd = DateTime(yearCalendar, monthOfYear + 1, dayOfMonth, 0, 0)
                },
                year,
                month,
                day
            )
            dpd.show()
        })

        filterViewModel.searchTasksEventLiveData.observe(activity!!, Observer {
            bundle.putParcelable("Filter", filterViewModel.filter)
            newFragment = FilteredTasksFragment()
            newFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).commit()
        })

        return binding.root
    }


}
