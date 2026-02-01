package com.intentlens.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class VoiceCapture(private val context: Context) {

  private var sr: SpeechRecognizer? = null

  fun start(onResult: (String) -> Unit, onError: (String) -> Unit) {
    if (!SpeechRecognizer.isRecognitionAvailable(context)) {
      onError("Speech recognition not available on this device.")
      return
    }
    sr = SpeechRecognizer.createSpeechRecognizer(context).apply {
      setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
        override fun onError(error: Int) { onError("ASR error code: $error") }
        override fun onResults(results: Bundle?) {
          val texts = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
          onResult(texts?.firstOrNull().orEmpty())
        }
      })

      val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
      }
      startListening(intent)
    }
  }

  fun stop() {
    sr?.stopListening()
    sr?.destroy()
    sr = null
  }
}
