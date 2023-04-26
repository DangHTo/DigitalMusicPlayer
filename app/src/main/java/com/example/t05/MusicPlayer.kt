package com.example.t05

import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer

import android.media.AudioManager
import android.media.SoundPool
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.activityViewModels
import java.io.IOException





class MusicPlayer(val musicService: MusicService): MediaPlayer.OnCompletionListener {


    val MUSICPATH = arrayOf("http://people.cs.vt.edu/~esakia/mario.mp3", "http://people.cs.vt.edu/~esakia/tetris.mp3")
    val MUSICNAME = arrayOf("Mario Theme Song", "Tetris Theme Song")

    var player: MediaPlayer? = null
    var musicIndex = 0

    private var musicStatus = 0//0: before starts 1: playing 2: paused

    //Start of soundPool
    private var soundpool: SoundPool
    private var music: Array<Int> = arrayOf(0, 0, 0)
    private var effects: Array<Int> = arrayOf(0, 0, 0)

    private var duration: Double = 0.0
    private var streamID: Int = 0
    private var effectsID: Array<Int> = arrayOf(0, 0, 0)

    private var loaded = false

    init{
        var audioAttributes: AudioAttributes =  AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        soundpool = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAttributes)
            .build()

        soundpool.setOnLoadCompleteListener{ soundpool, sampleId, status -> loaded = true}

        music[0] = soundpool.load(musicService.applicationContext, R.raw.gotechgo, 1)
        music[1] = soundpool.load(musicService.applicationContext, R.raw.mario, 1)
        music[2] = soundpool.load(musicService.applicationContext, R.raw.tetris, 1)
        effects[0] = soundpool.load(musicService.applicationContext, R.raw.clapping, 1)
        effects[1] = soundpool.load(musicService.applicationContext, R.raw.cheering, 1)
        effects[2] = soundpool.load(musicService.applicationContext, R.raw.lestgohokies, 1)
    }

    fun getMusicStatus(): Int {
        return musicStatus
    }

    fun getMusicName(effect: Int): Int {
        return effect
    }


    fun playMusic(musicSpot: Int, effectsArr: Array<Int>, effectsLocation: Array<Double>) {
        var audioAttributes: AudioAttributes =  AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        player = MediaPlayer()
        player!!.setAudioAttributes(audioAttributes)
        player!!.setDataSource(MUSICPATH[musicIndex])
        player!!.prepare()
        player!!.setOnCompletionListener(this)
        duration = player!!.duration.toDouble()


        streamID = soundpool.play(music[musicSpot], 0.5F, 0.5F, 1, 0, 1.0F)
        Log.d("Duration", duration.toString())

        effectsID[0] = soundpool.play(effects[effectsArr[0]], 0.5F, 0.5F, 1, 0, 1.0F)
        soundpool!!.pause(effectsID[0])
        Handler(Looper.getMainLooper())
            .postDelayed(
                {
                    soundpool.resume(effectsID[0])
                    getMusicName(effectsArr[0])
                },
                (effectsLocation[0] * duration).toLong()
            )

        effectsID[1] = soundpool.play(effects[effectsArr[1]], 0.5F, 0.5F, 1, 0, 1.0F)
        soundpool!!.pause(effectsID[1])
        Handler(Looper.getMainLooper())
            .postDelayed({soundpool.resume(effectsID[1])},
                (effectsLocation[1] * duration).toLong())

        effectsID[2] = soundpool.play(effects[effectsArr[2]], 0.5F, 0.5F, 1, 0, 1.0F)
        soundpool!!.pause(effectsID[2])
        Handler(Looper.getMainLooper())
            .postDelayed({soundpool.resume(effectsID[2])},
                (effectsLocation[2] * duration).toLong())

        musicStatus = 1


    }

    fun pauseMusic() {
        if (soundpool != null) {
            soundpool!!.pause(streamID)
            soundpool!!.pause(effectsID[0])
            soundpool!!.pause(effectsID[1])
            soundpool!!.pause(effectsID[2])
            musicStatus = 2
        }
    }

    fun resumeMusic() {
        if (soundpool != null) {
            soundpool.resume(streamID)
            soundpool.resume(effectsID[0])
            soundpool.resume(effectsID[1])
            soundpool.resume(effectsID[2])
            musicStatus = 1
        }
    }


    fun restartMusic(){
        pauseMusic()

        musicStatus = 0
    }


    override fun onCompletion(mp: MediaPlayer?) {
        player!!.release()
        player = null
    }


}