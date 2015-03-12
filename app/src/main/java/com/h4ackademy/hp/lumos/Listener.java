package com.h4ackademy.hp.lumos;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

public class Listener implements RecognitionListener {
    MainActivity mainActivity;

    public Listener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

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
        Log.i("opcion",String.valueOf(data.get(0)));
        String action = actionText(String.valueOf(data.get(0)));
        Log.i("Variable action", action);
        switch (action) {
            case "on":
                mainActivity.turn_On_flash();
                break;
            case "off":
                mainActivity.turn_Off_flash();
                break;
            default:
                mainActivity.understandWord();
                break;
        }

    }

    public String actionText (String data){
        String[] wordsOn = {"lunes", "lomos", "lumos", "lupus", "plomos", "lujos", "lobos"};
        String[] wordsOff = {"nox", "maps", "no", "nos"};

        for(String word : wordsOn){
            if(data.equalsIgnoreCase(word))
                return "on";
        }

        for(String word : wordsOff){
            if(data.equalsIgnoreCase(word))
                return "off";
        }

        return "nothing";
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
