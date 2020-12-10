package com.ikriz.e_voting.ui.status

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        statusViewModel.vote.observe(viewLifecycleOwner, Observer {
            val pieDataSet = PieDataSet(it, "").apply {
                colors = ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS)
            }
            val pieData = PieData(pieDataSet).apply {
                setValueTextSize(12f)
                setValueFormatter(PercentFormatter(root.chart))
                setValueTextColors(arrayListOf(Color.WHITE))
            }
            root.chart.apply {
                setEntryLabelTextSize(12f)
                setUsePercentValues(true)
                description.text = "*klik untuk memperbarui data"
                isDrawHoleEnabled = false
                animateXY(750, 750, EaseOutCirc)
                setTouchEnabled(true)
            }
            root.chart.data = pieData
            root.chart.animate()
        })
        return root
    }
}