package com.ikriz.e_voting

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.no_internet.*

class Login : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var sharedPref: SharedPreferences
    private lateinit var noInternetDialog: Dialog
    private val single = ReactiveNetwork.checkInternetConnectivity()
    private var internetDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar?.hide()
        sharedPref = getSharedPreferences(R.string.PREF_KEY.toString(), Context.MODE_PRIVATE)
        db = Firebase.firestore
        setNoInternetDialog()
        btn_login.setOnClickListener { checkInternetOnce(true) }
        btn_forget.setOnClickListener { showDialogForget() }
    }

    override fun onResume() {
        super.onResume()
        updateUI(sharedPref.getString(R.string.USER_KEY.toString(), "FIRST"))
        checkInternetOnce(false)
    }

    override fun onPause() {
        super.onPause()
        safelyDispose(internetDisposable)
    }

    private fun doLogin() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)

        val nik = et_nik.editText?.text.toString()
        val token = et_token.editText?.text.toString()

        if (nik.isEmpty()) {
            et_nik.error = "Mohon mengisi nik"
            et_nik.requestFocus()
            return
        } else {
            et_nik.error = null
        }

        if (token.isEmpty()) {
            et_token.error = "Mohon mengisi token"
            et_token.requestFocus()
            return
        } else {
            et_token.error = null
        }

        db.collection("Users").document(nik).get().addOnSuccessListener { doc ->
            if (doc.get("token") != null) {
                if (doc.get("token") == token) {
                    sharedPref.edit().apply {
                        putString(R.string.USER_KEY.toString(), nik)
                        apply()
                    }
                    updateUI(nik)
                } else {
                    et_token.error = "Token salah"
                    et_token.requestFocus()
                    updateUI(null)
                }
            } else {
                et_nik.error = "NIK salah"
                et_nik.requestFocus()
                updateUI(null)
            }
        }
    }

    private fun updateUI(currentUser: String?) {
        if (currentUser == "FIRST") return
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(baseContext, "Login gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setNoInternetDialog() {
        noInternetDialog = Dialog(this, android.R.style.Theme)
        noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        noInternetDialog.setContentView(R.layout.no_internet)
        noInternetDialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        noInternetDialog.btn_try_again.setOnClickListener {
            noInternetDialog.dismiss()
        }
    }

    private fun safelyDispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun showDialogForget() {
        AlertDialog.Builder(this).apply {
            setTitle("Lupa token?")
            setMessage("Silahkan hubungi RT/RW atau pemerintah setempat")
            setNegativeButton("OK") { dialog, _ ->
                dialog.cancel()
            }
            create()
        }.show()
    }

    private fun checkInternetOnce(doLogin: Boolean) {
        internetDisposable = single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet ->
                when (isConnectedToInternet) {
                    true -> {
                        noInternetDialog.dismiss()
                        if (doLogin) doLogin()
                    }
                    false -> noInternetDialog.show()
                }
            }
    }
}