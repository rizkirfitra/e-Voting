package com.ikriz.e_voting.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatusViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val listPaslon = MutableLiveData<ArrayList<PieEntry>>()
    private val listSuara = MutableLiveData<ArrayList<Int>>()
    private val dataPieChart: LiveData<ArrayList<PieEntry>> = listPaslon
    private val dataResult: LiveData<ArrayList<Int>> = listSuara

    init {
        loadData()
    }

    internal fun getPieChart(): LiveData<ArrayList<PieEntry>> {
        return dataPieChart
    }

    internal fun getResult(): LiveData<ArrayList<Int>> {
        return dataResult
    }

    fun refresh() {
        loadData()
    }

    private fun loadData() {
        db.collection("Users").get().addOnSuccessListener {
            var suara1 = 0
            var suara2 = 0
            var suara3 = 0
            for (doc in it) when (doc.get("vote")) {
                "1" -> suara1 += 1
                "2" -> suara2 += 1
                "3" -> suara3 += 1
            }
            listPaslon.value?.clear()
            listPaslon.value = arrayListOf(
                PieEntry(suara1.toFloat(), "Paslon 1"),
                PieEntry(suara2.toFloat(), "Paslon 2"),
                PieEntry(suara3.toFloat(), "Paslon 3")
            )
            listSuara.value?.clear()
            listSuara.value = arrayListOf(suara1, suara2, suara3)
        }
    }
}