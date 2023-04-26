package com.example.t05

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class EditFragment : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.playButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_editFragment_to_playFragment)
        }

        /**
         * Seek bar listeners
         */
        view.findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seek: SeekBar?) {

            }

            override fun onStopTrackingTouch(seek: SeekBar?) {
                viewModel.soundPositions[0] = seek?.progress?.toDouble()!! / 100.0
                Log.d("SeekBar1 Position", viewModel.soundPositions[0].toString())
            }

        })

        view.findViewById<SeekBar>(R.id.seekBar2).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seek: SeekBar?) {

            }

            override fun onStopTrackingTouch(seek: SeekBar?) {
                viewModel.soundPositions[1] = seek?.progress?.toDouble()!! / 100.0
            }

        })

        view.findViewById<SeekBar>(R.id.seekBar3).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seek: SeekBar?) {

            }

            override fun onStopTrackingTouch(seek: SeekBar?) {
                viewModel.soundPositions[2] = seek?.progress?.toDouble()!! / 100.0
            }

        })



        /**
         * The background music spinner
         */
        val bgdMusicSpinner: Spinner = view.findViewById(R.id.bgdMusicSpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.bgd_music_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            bgdMusicSpinner.adapter = adapter
        }

        bgdMusicSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.songTitle = position
            }
        }

        /**
         * The background music spinner
         */
        val spinner1: Spinner = view.findViewById(R.id.spinner2)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.sound_effect_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner1.adapter = adapter
        }

        spinner1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.sounds[0] = position
            }
        }

        /**
         * The background music spinner
         */
        val spinner2: Spinner = view.findViewById(R.id.spinner3)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.sound_effect_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner2.adapter = adapter
        }

        spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.sounds[1] = position
            }
        }

        /**
         * The background music spinner
         */
        val spinner3: Spinner = view.findViewById(R.id.spinner4)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.sound_effect_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner3.adapter = adapter
        }

        spinner3.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.sounds[2] = position
            }
        }

    }
}