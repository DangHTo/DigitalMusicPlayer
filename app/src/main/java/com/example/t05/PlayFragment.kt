package com.example.t05

import android.content.*
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.t05.MainActivity.Companion.TAG
import com.example.t05.MainActivity.Companion.USERNAME

class PlayFragment : Fragment(), View.OnClickListener {

    private val viewModel: MyViewModel by activityViewModels()

    lateinit var restartButton: Button

    override fun onClick(v: View?) {


        if(isBound && v!!.id == R.id.play){
            when (musicService?.getPlayingStatus()) {
                0 -> {
                    musicService?.startMusic(viewModel.songTitle, viewModel.sounds, viewModel.soundPositions)
                    play?.setText("Pause")
                    appendEvent("Music Started")

                    restartButton.isEnabled = false
                }
                1 -> {
                    musicService?.pauseMusic()
                    play?.setText("Resume")
                    appendEvent("Music Resumed")

                    restartButton.isEnabled = true
                }
                2 ->{
                    musicService?.resumeMusic()
                    play?.setText("Pause")
                    appendEvent("Music Paused")

                    restartButton.isEnabled = false
                }

            }

        }

        else{
            musicService?.restartMusic()
            appendEvent("Music Restarted")

            restartButton.isEnabled = true
            play?.setText("Start")
        }
    }

    val INITIALIZE_STATUS = "intialization status"
    val MUSIC_PLAYING = "music playing"

    var play: Button? = null
    var music: TextView? = null


    var musicService: MusicService? =null
    var musicCompletionReceiver: MusicCompletionReceiver?=null
    var startMusicServiceIntent: Intent?  =null
    var isInitialized = false
    var isBound = false

    private val musicServiceConnection = object: ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false

        }

        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {

            var binder = iBinder as MusicService.MyBinder
            musicService = binder.getService()
            isBound = true
            when (musicService?.getPlayingStatus()){
                0 -> play?.text = "Start"
                1 -> play?.text = "Pause"
                2 -> play?.text = "Resume"

            }

        }


    }

    private fun appendEvent(event: String){

        WorkManager.getInstance().beginUniqueWork(TAG, ExistingWorkPolicy.KEEP, OneTimeWorkRequestBuilder<UploadWorker>().setInputData(
            workDataOf("username" to USERNAME, "event" to event)
        )
            .build()).enqueue()
    }



    //OnCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.play_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        play = view.findViewById(R.id.play)
        music = view.findViewById(R.id.music)
        play?.setOnClickListener(this)

        restartButton = view.findViewById<Button>(R.id.restart_button)
        restartButton.setOnClickListener(this)

        //Setting image
        when(viewModel.songTitle) {
            0 -> view.findViewById<ImageView>(R.id.music_icon)
                .setImageResource(R.drawable.gotechgo)

            1 -> view.findViewById<ImageView>(R.id.music_icon)
                .setImageResource(R.drawable.marionsmbudeluxe)

            2 -> view.findViewById<ImageView>(R.id.music_icon)
                .setImageResource(R.drawable.tetris)
        }

        view.findViewById<TextView>(R.id.music).text = viewModel.songNames.get(viewModel.songTitle)
        startMusicServiceIntent = Intent(view.context, MusicService::class.java)
        if(!isInitialized){
            this.context?.startService(startMusicServiceIntent)
            isInitialized = true
        }
        musicCompletionReceiver =MusicCompletionReceiver(this)
    }


    fun updateName(musicName: String?) {
        music?.text = musicName
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

        if(isInitialized && !isBound)
            this.context?.bindService(startMusicServiceIntent,musicServiceConnection, Context.BIND_AUTO_CREATE)

        this.context?.registerReceiver(musicCompletionReceiver, IntentFilter(MusicService.COMPLETE_INTENT))
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isBound){

            this.context?.unbindService(musicServiceConnection)
            isBound = false
        }
        this.context?.unregisterReceiver(musicCompletionReceiver)
    }

}