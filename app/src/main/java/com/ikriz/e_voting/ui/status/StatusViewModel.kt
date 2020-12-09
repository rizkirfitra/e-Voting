package com.ikriz.e_voting.ui.status

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatusViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val pieEntries = ArrayList<PieEntry>()
    private val _vote = MutableLiveData<ArrayList<PieEntry>>()

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
            pieEntries += PieEntry(counter1.toFloat().div(100f), "Paslon 1")
            pieEntries += PieEntry(counter2.toFloat().div(100f), "Paslon 2")
            pieEntries += PieEntry(counter3.toFloat().div(100f), "Paslon 3")
            _vote.value = pieEntries
            Log.d("ENRTY", pieEntries.size.toString())
            Log.d("_VOTE", _vote.value!!.size.toString())
        }

    }

    val vote: LiveData<ArrayList<PieEntry>> = _vote

}