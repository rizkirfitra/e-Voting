package com.ikriz.e_voting

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.edit_profile.*

class EditProfile : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)
        db = Firebase.firestore
        sharedPref = getSharedPreferences(R.string.PREF_KEY.toString(), Context.MODE_PRIVATE)
        val user = sharedPref.getString(R.string.USER_KEY.toString(), null)

        if (user != null) {
            db.collection("Users").document(user).addSnapshotListener { value, _ ->
                Log.d("TESS","error")
                et_nama.editText?.setText(value!!["nama"].toString())
                et_telepon.editText?.setText(value!!["telepon"].toString())
                et_alamat.editText?.setText(value!!["alamat"].toString())
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }
        btn_save.setOnClickListener {
            doSave(user)
        }
    }

    private fun doSave(user: String?) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)

        val nama = et_nama.editText?.text.toString()
        val telepon = et_telepon.editText?.text.toString()
        val alamat = et_alamat.editText?.text.toString()

        if (nama.isEmpty()) {
            et_nama.error = "Mohon mengisi nama"
            et_nama.requestFocus()
            return
        } else {
            et_nama.error = null
        }

        if (telepon.isEmpty()) {
            et_telepon.error = "Mohon mengisi no. telepon"
            et_telepon.requestFocus()
            return
        } else {
            et_telepon.error = null
        }

        if (alamat.isEmpty()) {
            et_alamat.error = "Mohon mengisi alamat"
            et_alamat.requestFocus()
            return
        } else {
            et_alamat.error = null
        }

        if (user != null) {
            db.collection("Users").document(user).update(
                hashMapOf("nama" to nama, "telepon" to telepon, "alamat" to alamat) as Map<String, Any>
            ).addOnSuccessListener {
                Toast.makeText(this,"Berhasil mengubah data",Toast.LENGTH_LONG).show()
                finish()
            }
        } else Toast.makeText(this,"Gagal mengubah data",Toast.LENGTH_LONG).show()
    }
}
