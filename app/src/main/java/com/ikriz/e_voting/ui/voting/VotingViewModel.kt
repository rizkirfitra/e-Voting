package com.ikriz.e_voting.ui.voting

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikriz.e_voting.R

class VotingViewModel : ViewModel() {

//    private val sharedPref = getApplication<Application>().getSharedPreferences(
//        R.string.PREF_KEY.toString(),
//        Context.MODE_PRIVATE
//    )
//    private val user = sharedPref.getString(R.string.USER_KEY.toString(), null)

    private val _text = MutableLiveData<String>().apply {
        value = "This is voting Fragment"
    }
    val text: LiveData<String> = _text
}