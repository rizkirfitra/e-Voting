package com.ikriz.e_voting.ui.status

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatusViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val data = ArrayList<DataEntry>()
    private val _vote = MutableLiveData<ArrayList<DataEntry>>()

    init {
        db.collection("Users").get().addOnSuccessListener {
            val counter1 = 0
            val counter2 = 0
            val counter3 = 0
            for (doc in it) when (doc["vote"]) {
                1 -> counter1.plus(1)
                2 -> counter2.plus(1)
                3 -> counter2.plus(1)
            }
            data += ValueDataEntry("Paslon 1",counter1)
            data += ValueDataEntry("Paslon 2",counter2)
            data += ValueDataEntry("Paslon 3",counter3)
            _vote.value = data
            Log.d("ENRTY", data.size.toString())
            Log.d("_VOTE", _vote.value!!.size.toString())
        }

    }

    val vote: LiveData<ArrayList<DataEntry>> = _vote

}