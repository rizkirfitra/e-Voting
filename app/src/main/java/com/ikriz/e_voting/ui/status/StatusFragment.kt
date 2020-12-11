package com.ikriz.e_voting.ui.status

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing.EaseOutCirc
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.ikriz.e_voting.R
import kotlinx.android.synthetic.main.fragment_status.view.*


class StatusFragment : Fragment() {

    private lateinit var statusViewModel: StatusViewModel
    private var lastClickTime:Long = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        val pieChart = root.chart
        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)

        statusViewModel.getPieChart().observe(viewLifecycleOwner, Observer {
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
                description.text = "*tap untuk memperbaharui data"
                isDrawHoleEnabled = false
                animateXY(750, 750, EaseOutCirc)
                setTouchEnabled(false)
            }
            pieChart.data = pieData
            pieChart.invalidate()
        })

        statusViewModel.getResult().observe(viewLifecycleOwner, Observer {
            root.suara1.text = "${it[0]} suara"
            root.suara2.text = "${it[1]} suara"
            root.suara3.text = "${it[2]} suara"
        })

        pieChart.setOnClickListener {
            if (SystemClock.elapsedRealtime() - lastClickTime < 3000) return@setOnClickListener
            lastClickTime = SystemClock.elapsedRealtime()
            statusViewModel.refresh()
        }
        return root
    }

}