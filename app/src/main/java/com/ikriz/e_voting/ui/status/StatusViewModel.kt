package com.ikriz.e_voting.ui.status

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.eazegraph.lib.models.PieModel

class StatusViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val data = ArrayList<PieModel>()
    private val _vote = MutableLiveData<ArrayList<PieModel>>()

    init {
        db.collection("Users").get(Source.CACHE).addOnSuccessListener {
            var counter1 = 0
            var counter2 = 0
            var counter3 = 0
            for (doc in it) when (doc.get("vote")) {
                "1" -> counter1 += 1
                "2" -> counter2 += 1
                "3" -> counter3 += 1
            }
            data += PieModel("Paslon 1", counter1.toFloat(), Color.parseColor("#FE6DA8"))
            data += PieModel("Paslon 2", counter2.toFloat(), Color.parseColor("#56B7F1"))
            data += PieModel("Paslon 3", counter3.toFloat(), Color.parseColor("#CDA67F"))
            _vote.value = data
            Log.d("ENRTY", data.size.toString())
            Log.d("_VOTE", _vote.value!!.size.toString())
        }
    }
    val vote: LiveData<ArrayList<PieModel>> = _vote
}