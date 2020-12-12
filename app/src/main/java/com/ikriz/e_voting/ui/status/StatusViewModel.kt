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
    private val listPaslon = MutableLiveData<ArrayList<PieEntry>>()
    val dataPieChart: LiveData<ArrayList<PieEntry>> = listPaslon

    init {
        db.collection("Users").addSnapshotListener { value, error ->
            if (error != null) {
                Log.w("SNAPSHOT", "Listen failed.", error)
                return@addSnapshotListener
            }
            var suara1 = 0
            var suara2 = 0
            var suara3 = 0
            for (doc in value!!) when (doc.get("vote")) {
                "1" -> suara1 += 1
                "2" -> suara2 += 1
                "3" -> suara3 += 1
            }
            listPaslon.value = arrayListOf(
                PieEntry(suara1.toFloat(), "Paslon 1"),
                PieEntry(suara2.toFloat(), "Paslon 2"),
                PieEntry(suara3.toFloat(), "Paslon 3")
            )
        }
    }
}