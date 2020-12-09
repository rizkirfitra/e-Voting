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
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
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
            Log.d("VOTE", it.size.toString())

            val pieDataSet = PieDataSet(it, "")
            Log.d("DS", pieDataSet.toString())
//            pieDataSet.valueTextSize = 12f
            pieDataSet.colors = ColorTemplate.createColors(ColorTemplate.VORDIPLOM_COLORS)

            val pieData = PieData(pieDataSet)
//            pieData.setValueTextSize(11f)

            root.piechart.data = pieData
//            root.piechart.setEntryLabelTextSize(12f)
//            root.piechart.setUsePercentValues(true)
//            root.piechart.isDrawHoleEnabled = false
//            root.piechart.setEntryLabelColor(Color.BLACK)
//            root.piechart.description.isEnabled = false
            root.piechart.visibility=View.VISIBLE
            root.piechart.invalidate()
        })
        return root
    }
}