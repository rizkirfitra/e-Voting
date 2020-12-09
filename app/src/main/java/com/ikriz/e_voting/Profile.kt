package com.ikriz.e_voting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile.*

class Profile : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        supportActionBar?.elevation= 0F

        sharedPref = getSharedPreferences(R.string.PREF_KEY.toString(), Context.MODE_PRIVATE)
        val user = sharedPref.getString(R.string.USER_KEY.toString(),null)
        db = Firebase.firestore

        user?.let {
            db.collection("Users").document(it).get().addOnSuccessListener { doc ->
                if (doc != null) {
                    nama.text = doc.get("nama").toString()
                    nik.text = user
                    telepon.text = doc.get("telepon").toString()
                    alamat.text = doc.get("alamat").toString()
                } else {
                    nama.text = "-"
                    nik.text = "-"
                    telepon.text = "-"
                    alamat.text = "-"
                }
            }
        }

        btn_logout.setOnClickListener {
            with(sharedPref.edit()) {
                putString(R.string.USER_KEY.toString(), null)
                apply()
            }
            startActivity(Intent(this, Login::class.java))
            setResult(RESULT_OK, null);
            db.clearPersistence()
            finish()
        }
    }
}