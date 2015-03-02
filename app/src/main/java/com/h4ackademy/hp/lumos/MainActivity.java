package com.h4ackademy.hp.lumos;

import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Camera.Parameters;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Camera cam;
    private Parameters p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button torch = (Button) findViewById(R.id.linterna);
        torch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cam == null) {
                    turn_On_flash();
                } else {
                    turn_Off_flash();
                }
            }
        });


    }

    public void turn_On_flash(){
            cam = Camera.open();
            p = cam.getParameters();
            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            cam.startPreview();
    }

    public void turn_Off_flash(){
        cam.stopPreview();
        cam.release();
        cam = null;
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
