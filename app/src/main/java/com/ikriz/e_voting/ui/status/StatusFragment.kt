package com.ikriz.e_voting.ui.status

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        val pieChart = root.chart
        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)

        statusViewModel.dataPieChart.observe(viewLifecycleOwner, Observer {
            for (paslon in it) when (paslon.label) {
                "Paslon 1" -> root.suara1.text = "${paslon.value.toInt()} suara"
                "Paslon 2" -> root.suara2.text = "${paslon.value.toInt()} suara"
                "Paslon 3" -> root.suara3.text = "${paslon.value.toInt()} suara"
            }
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
                description.text = "*diperbaharui otomatis"
                isDrawHoleEnabled = false
                setTouchEnabled(false)
                data = pieData
            }
            pieChart.invalidate()
        })
        return root
    }
}