package com.example.md.givename;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Random;

public class Shake extends AppCompatActivity implements SensorEventListener {

    SensorManager sm;
    public ArrayList<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //get x, y, z values
            float value[] = event.values;
            float x = value[0];
            float y = value[1];
            float z = value[2];
            //use the following formula
            //use gravity according to your place if you are on moon than use moon gravity
            float asr = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH *
                    SensorManager.GRAVITY_EARTH);
            //If mobile move any direction then the following condition will become true
            if (asr >= 2) {
                //Generate random number every time and display on text view
//                    result();
                }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

