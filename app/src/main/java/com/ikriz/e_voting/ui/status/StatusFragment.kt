package com.ikriz.e_voting.ui.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.ikriz.e_voting.R
import kotlinx.android.synthetic.main.fragment_status.*
import kotlinx.android.synthetic.main.fragment_status.view.*


class StatusFragment : Fragment() {

    private lateinit var statusViewModel: StatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        statusViewModel.vote.observe(viewLifecycleOwner, Observer {
            root.chart.setProgressBar(progress_bar)
            val pie = AnyChart.pie()

            pie.data(it)
            pie.title("Quick Count")
            pie.labels().position("outside")
            pie.legend().title().enabled(true)
            pie.legend().title()
                .text("Retail channels")
                .padding(0.0, 0.0, 10.0, 0.0)
            pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER)

            root.chart.setChart(pie)
        })
        return root
    }
}