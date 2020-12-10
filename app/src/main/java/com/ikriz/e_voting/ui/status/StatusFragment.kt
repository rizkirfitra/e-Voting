package com.ikriz.e_voting.ui.status

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ikriz.e_voting.R
import org.eazegraph.lib.charts.PieChart


class StatusFragment : Fragment() {

    private lateinit var statusViewModel: StatusViewModel
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        statusViewModel.vote.observe(viewLifecycleOwner, Observer {
            pieChart = root.findViewById(R.id.chart)
            for (pieModel in it) pieChart.addPieSlice(pieModel)
            Log.d("PIE", it.size.toString())
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        pieChart = view?.findViewById(R.id.chart)!!
        pieChart.startAnimation()
    }
}