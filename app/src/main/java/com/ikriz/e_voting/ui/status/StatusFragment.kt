package com.ikriz.e_voting.ui.status

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.ikriz.e_voting.R
import kotlinx.android.synthetic.main.fragment_status.view.*
import kotlinx.android.synthetic.main.list_rincian.view.*


class StatusFragment : Fragment() {

    private lateinit var statusViewModel: StatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        val pieChart = root.chart
        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)

        statusViewModel.dataPieChart.observe(viewLifecycleOwner, Observer {
            class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
                RecyclerView.ViewHolder(inflater.inflate(R.layout.list_rincian, parent, false)) {
                var paslon = itemView.paslon
                var suara = itemView.suara
            }
            root.recycler_rincian.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = object : RecyclerView.Adapter<MyViewHolder>() {
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): MyViewHolder {
                        return MyViewHolder(inflater, parent)
                    }

                    override fun getItemCount(): Int = it.size

                    @SuppressLint("SetTextI18n")
                    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                        holder.paslon.text = it[position].label
                        holder.suara.text = it[position].value.toInt().toString() + " suara"
                    }
                }
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
                legend.isWordWrapEnabled = true
            }
            pieChart.data = pieData
            pieChart.invalidate()
        })
        return root
    }
}