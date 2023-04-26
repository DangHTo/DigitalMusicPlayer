package com.example.t05

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MyViewModel(application: Application): AndroidViewModel(application){
    var songNames: Array<String> = arrayOf("Go Tech Go", "Mario Theme Song", "Tetris Theme Song")


    var songTitle: Int = 0
    var sounds: Array<Int> = arrayOf(0, 0, 0)

    var soundPositions: Array<Double> = arrayOf(0.0, 0.0, 0.0)
}