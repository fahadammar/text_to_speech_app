package com.example.texttospeechdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeechdemoapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    val TAG = "textToSpeech"
    private var textToSpeech : TextToSpeech? = null
    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Making the instance of the Text To Speech
        textToSpeech = TextToSpeech(this, this)

        // Button Speak On Click Listener
        binding?.btnSpeak?.setOnClickListener{
            if(binding?.enterText?.text!!.isEmpty()){
                showToast(R.string.plzEnterTxt.toString(), false)
            } else {
                speakEnteredText(binding?.enterText?.text.toString())
            }
        }

    }

    /**
     * This the TextToSpeech override function
     *
     * Called to signal the completion of the TextToSpeech engine initialization.
     */
    // TEXT TO SPEECH IMPLEMENTED METHOD
    override fun onInit(status: Int) {
        if(status != TextToSpeech.ERROR){
            val langAvailability = textToSpeech?.setLanguage(Locale.UK)
            if (langAvailability == TextToSpeech.LANG_MISSING_DATA || langAvailability == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, R.string.langNoSupport.toString())
                showToast(R.string.langNoSupport.toString(), false)
            }
        }
    }

    // The Function To execute to speak functionality of Text To Speech
    private fun speakEnteredText(text : String){
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    // THE FUNCTION TO SHOW THE TOAST
    private fun showToast(toastMessage : String, longToast: Boolean){
        if(longToast){
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // OVERRIDE ON DESTROY METHOD
    override fun onDestroy() {
        if(textToSpeech != null){
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }
        super.onDestroy()
    }
}

/**
 * URLs For the Text To Speech - The Documentation and Example
 * 1 -> https://is.gd/IbX0o0
 * 2 -> https://developer.android.com/reference/kotlin/android/speech/tts/TextToSpeech.OnInitListener
 */