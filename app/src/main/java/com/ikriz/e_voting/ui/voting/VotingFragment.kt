package com.ikriz.e_voting.ui.voting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ikriz.e_voting.R

class VotingFragment : Fragment() {

    private lateinit var votingViewModel: VotingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        votingViewModel =
            ViewModelProvider(this).get(VotingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_voting, container, false)
//        val textView: TextView = root.findViewById(R.id.text_voting)
        votingViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })
        return root
    }
}