package com.ikriz.e_voting.ui.status

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing.EaseOutCirc
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.ikriz.e_voting.R
import kotlinx.android.synthetic.main.fragment_status.*
import kotlinx.android.synthetic.main.fragment_status.view.*


class StatusFragment : Fragment() {

    private lateinit var statusViewModel: StatusViewModel
    private lateinit var pieChart: PieChart
    private lateinit var refresh: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        pieChart = root.chart
        return root
    }

    private fun updateChart(statusViewModel: StatusViewModel) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)
        statusViewModel.vote.observe(viewLifecycleOwner, Observer {
            val pieDataSet = PieDataSet(it, "").apply {
                colors = ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS)
            }
            val pieData = PieData(pieDataSet).apply {
                setValueTextSize(12f)
                setValueFormatter(PercentFormatter(pieChart))
                setValueTextColors(arrayListOf(Color.WHITE))
            }
            pieChart.apply {
                setEntryLabelTextSize(12f)
                setUsePercentValues(true)
                description.text = "*klik untuk memperbarui data"
                isDrawHoleEnabled = false
                animateXY(750, 750, EaseOutCirc)
            }
            pieChart.data = pieData
            pieChart.invalidate()
        })
        view.btn_refresh.setOnClickListener {
            Toast.makeText(context, "YEAY", Toast.LENGTH_LONG).show()
        }
    }
}