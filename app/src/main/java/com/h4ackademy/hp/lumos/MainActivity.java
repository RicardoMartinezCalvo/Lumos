package com.h4ackademy.hp.lumos;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Camera.Parameters;
import android.speech.SpeechRecognizer;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private Camera cam;
    private boolean camState = false;
    private SpeechRecognizer speech;
    private Intent record;
    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Displays the acction bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        speech = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        speech.setRecognitionListener(new Listener(this));
        record = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        record.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 0);
        record.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 0);

        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

    }

    public void startRecord(){
        speech.startListening(record);
    }

    public void turn_On_flash(){
        if(!camState) {
            cam = Camera.open();
            Parameters p = cam.getParameters();
            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            cam.startPreview();
            camState = true;
        }
    }

    public void turn_Off_flash(){
        if(camState) {
            cam.stopPreview();
            cam.release();
            camState = false;
        }
    }

    public void understandWord(){
        Toast toast = Toast.makeText
                (this,"I can't understand the word", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onSensorChanged (SensorEvent event)  {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = FloatMath.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if(mAccel > 4){
                startRecord();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorMan.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMan.unregisterListener(this);
        turn_Off_flash();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
