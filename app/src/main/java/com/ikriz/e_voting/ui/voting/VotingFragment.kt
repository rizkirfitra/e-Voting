package com.ikriz.e_voting.ui.voting

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ikriz.e_voting.R
import kotlinx.android.synthetic.main.fragment_voting.view.*

class VotingFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = requireActivity().getSharedPreferences(
            R.string.PREF_KEY.toString(),
            Context.MODE_PRIVATE
        )
        val root = inflater.inflate(R.layout.fragment_voting, container, false)
        val user = sharedPref.getString(R.string.USER_KEY.toString(), null)
        db.collection("Users").document(user!!).addSnapshotListener { value, error ->
            if (error != null) {
                Log.w("SNAPSHOT", "listen:error", error)
                return@addSnapshotListener
            }
            if (value!!.get("vote") == "") setVote(root, true)
            else setVote(root, false)
        }
        root.btn_vote1.setOnClickListener {
            showConfirmDialog(requireContext(), root.paslon1, user, 1)
        }
        root.btn_vote2.setOnClickListener {
            showConfirmDialog(requireContext(), root.paslon2, user, 2)
        }
        root.btn_vote3.setOnClickListener {
            showConfirmDialog(requireContext(), root.paslon3, user, 3)
        }
        return root
    }

    private fun setVote(view: View, isEnable: Boolean) {
        view.btn_vote1.isEnabled = isEnable
        view.btn_vote2.isEnabled = isEnable
        view.btn_vote3.isEnabled = isEnable
        view.status.text = if (isEnable) "belum vote" else "sudah vote"
    }

    private fun showConfirmDialog(context: Context, paslon: TextView, nik: String, vote: Int) {
        AlertDialog.Builder(context).apply {
            setMessage("Apakah Anda yakin memvote ${paslon.text} ?")
            setPositiveButton("Ya") { dialog, _ ->
                db.collection("Users").document(nik).update("vote", vote.toString())
                dialog.dismiss()
            }
            setNegativeButton("Batal") { dialog, _ ->
                dialog.cancel()
            }
            create()
        }.show()
    }
}