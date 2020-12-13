package com.ikriz.e_voting.ui.status

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatusViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val listPaslon = MutableLiveData<ArrayList<PieEntry>>()
    val dataPieChart: LiveData<ArrayList<PieEntry>> = listPaslon

    init {
        val voices = mutableMapOf<String, Int>()
        val data = arrayListOf<PieEntry>()
        db.collection("Voices").addSnapshotListener { docs, _ ->
            db.collection("Candidates").addSnapshotListener { candidates, _ ->
                for (candidate in candidates!!) {
                    voices[candidate.id] = 0
                }
                for (doc in docs!!) for (key in voices.keys) if (doc["vote"].toString() == key) {
                    voices[key] = voices[key]!! + 1
                }
                data.clear()
                for (voice in voices) for (candidate in candidates) if (voice.key == candidate.id) {
                    data.add(
                        PieEntry(
                            voice.value.toFloat(), candidate.get("nama").toString()
                        )
                    )
                }
                listPaslon.value = data
            }
        }
    }
}