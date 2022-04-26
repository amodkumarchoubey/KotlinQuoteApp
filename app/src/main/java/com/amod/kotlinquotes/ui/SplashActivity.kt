package com.amod.kotlinquotes.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amod.kotlinquotes.R
import com.amod.kotlinquotes.model.Quote
import com.amod.kotlinquotes.viewmodel.QuoteViewModal
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject


class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 3000
    private val TAG = "Splash_Screen"
    var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        progressBar = findViewById(R.id.ProgressBar)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}