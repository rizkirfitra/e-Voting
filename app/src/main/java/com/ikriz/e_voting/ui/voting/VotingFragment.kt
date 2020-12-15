package com.ikriz.e_voting.ui.voting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ikriz.e_voting.R
import kotlinx.android.synthetic.main.fragment_voting.view.*
import kotlinx.android.synthetic.main.list_rincian.view.paslon
import kotlinx.android.synthetic.main.list_vote.view.*
import java.util.concurrent.TimeUnit

class VotingFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = requireActivity().getSharedPreferences(R.string.PREF_KEY.toString(), Context.MODE_PRIVATE)
        val root = inflater.inflate(R.layout.fragment_voting, container, false)
        val user = sharedPref.getString(R.string.USER_KEY.toString(), null)

        db.collection("Candidates").addSnapshotListener { candidates, _ ->
            class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
                RecyclerView.ViewHolder(inflater.inflate(R.layout.list_vote, parent, false)) {
                var paslon = itemView.paslon
                var btnVote = itemView.btn_vote
            }
            root.recycler_vote.apply {
                layoutManager = LinearLayoutManager(root.context)
                adapter = object : RecyclerView.Adapter<MyViewHolder>() {
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): MyViewHolder {
                        return MyViewHolder(inflater, parent)
                    }

                    override fun getItemCount(): Int = candidates!!.size()

                    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                        holder.paslon.text = candidates!!.documents[position]["nama"].toString()
                        holder.btnVote.isEnabled = false
                        db.collection("Voices").whereEqualTo("user", user)
                            .addSnapshotListener { value, _ ->
                                holder.btnVote.isEnabled = value!!.isEmpty
                                root.status.text = if (value.isEmpty) "belum vote" else "sudah vote"
                                holder.itemView.btn_vote.setOnClickListener {
                                    showConfirmDialog(
                                        requireContext(),
                                        holder.paslon.text.toString(),
                                        user!!,
                                        position
                                    )
                                }
                            }
                    }
                }
            }
        }
        return root
    }

    private fun showConfirmDialog(context: Context, paslon: String, user: String, position: Int) {
        val dialog = AlertDialog.Builder(context).apply {
            setTitle("Peringatan!")
            setMessage("Anda hanya dapat melakukan vote satu kali dan tidak dapat mengubahnya. Apakah Anda yakin melakukan vote untuk $paslon?")
            setPositiveButton("Ya") { dialog, which ->
                db.collection("Voices").add(
                    hashMapOf("user" to user, "vote" to "paslon${position + 1}")
                )
                dialog.dismiss()
            }
            setNegativeButton("Batal") { dialog, _ ->
                dialog.cancel()
            }
        }.create()
        dialog.show()
        val btnText = dialog.getButton(AlertDialog.BUTTON_POSITIVE).text
        object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
                    isEnabled = true
                    text = btnText
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
                    isEnabled = false
                    text = String.format("%s (%d)", btnText, TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1)
                }
            }
        }.start()
    }
}