package com.ikriz.e_voting.ui.status

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatusViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val pieEntries = ArrayList<PieEntry>()
    private val _vote = MutableLiveData<ArrayList<PieEntry>>()
    val vote: LiveData<ArrayList<PieEntry>> = _vote

    init {
        db.collection("Users").get().addOnSuccessListener {
            var counter1 = 0
            var counter2 = 0
            var counter3 = 0
            for (doc in it) when (doc.get("vote")) {
                "1" -> counter1 += 1
                "2" -> counter2 += 1
                "3" -> counter3 += 1
            }
            pieEntries += PieEntry(counter1.toFloat(), "Paslon 1")
            pieEntries += PieEntry(counter2.toFloat(), "Paslon 2")
            pieEntries += PieEntry(counter3.toFloat(), "Paslon 3")
            _vote.value = pieEntries
        }
    }

//    private fun loadData() {
//        db.collection("Users").get().addOnSuccessListener {
//            var counter1 = 0
//            var counter2 = 0
//            var counter3 = 0
//            for (doc in it) when (doc.get("vote")) {
//                "1" -> counter1 += 1
//                "2" -> counter2 += 1
//                "3" -> counter3 += 1
//            }
//            pieEntries += PieEntry(counter1.toFloat(), "Paslon 1")
//            pieEntries += PieEntry(counter2.toFloat(), "Paslon 2")
//            pieEntries += PieEntry(counter3.toFloat(), "Paslon 3")
//            _vote.value = pieEntries
//        }
//    }
//
//    fun refresh() {
//        loadData()
//    }

}