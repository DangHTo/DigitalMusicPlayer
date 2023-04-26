package com.example.t05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View



class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Music Player"
        const val USERNAME = "Dang To"
        const val URL = "https://posthere.io/"
        const val ROUTE = "af00-4669-a995"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
