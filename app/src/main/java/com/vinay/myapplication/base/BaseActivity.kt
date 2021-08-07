package com.vinay.myapplication.base

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vinay.myapplication.R
import com.vinay.myapplication.MovieApplication
import com.vinay.myapplication.local.UserSharedPrefs
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(),MovieApplication.InternetConnectionListener{


    @Inject
    lateinit var userSharedPrefs: UserSharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MovieApplication).component?.inject(this)
        (application as MovieApplication).setInternetConnectionListener(this)

        super.onCreate(savedInstanceState)
    }

    override fun onInternetAvailable() {
//        TODO("Not yet implemented")
        Handler(mainLooper).post {
            Toast.makeText(application, "Connected", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onInternetUnavailable() {
       // Timber.e("No INTERNET","Your phone is not connected to internet")
        Handler(mainLooper).post {
            Toast.makeText(application, "Your phone is not connected to internet...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCacheUnavailable() {
    }

    var builder: AlertDialog.Builder? = null
    var dialog: Dialog? = null

    fun setDialog(show: Boolean, isCancelable: Boolean) {
        if (builder == null) {
            builder = AlertDialog.Builder(this, R.style.CustomProgressDialog)
            builder!!.setView(R.layout.view_progress_dialog)

        }
        if (dialog == null) {
            dialog = builder!!.create()
            dialog?.setCancelable(isCancelable)
        }

        //View view = getLayoutInflater().inflate(R.layout.progress);
        try {
            if (show)
                dialog!!.show()
            else
                dialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}