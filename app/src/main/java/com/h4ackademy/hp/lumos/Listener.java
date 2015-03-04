package com.h4ackademy.hp.lumos;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

public class Listener implements RecognitionListener {
    MainActivity mainActivity = new MainActivity();
    @Override
    public void onReadyForSpeech(Bundle params) {
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if (String.valueOf(data.get(0)).equalsIgnoreCase("lunes") ||
                String.valueOf(data.get(0)).equalsIgnoreCase("lomos")||
                String.valueOf(data.get(0)).equalsIgnoreCase("lumos") ||
                String.valueOf(data.get(0)).equalsIgnoreCase("lupus")) {
            mainActivity.turn_On_flash();
        }
        if (String.valueOf(data.get(0)).equalsIgnoreCase("nox") ||
                String.valueOf(data.get(0)).equalsIgnoreCase("maps")||
                String.valueOf(data.get(0)).equalsIgnoreCase("no")) {
            mainActivity.turn_Off_flash();
        }
        //Log.i("opcion",String.valueOf(data.get(0)));
    }



    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
