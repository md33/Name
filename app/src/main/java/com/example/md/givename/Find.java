package com.example.md.givename;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Find extends AppCompatActivity implements SensorEventListener{
    SensorManager sm;
    public DatabaseHandler db;
    public Spinner name , type;
    public RadioButton male ,female;
    public RadioGroup group;
    public  ArrayList<String>names;
    public int state  = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        db = new DatabaseHandler(this);
        name =(Spinner)findViewById(R.id.ner);
        type = (Spinner)findViewById(R.id.type);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        set();

    }
    public void onClick(android.view.View view){
        switch (view.getId())
        {
            case R.id.begin:
                String gen = null,name =null ,type=null;
                gen = gender();
                name=name();
                type=type();
                Log.w("MyApp", "ASD  GENDER : " + gen + " NAME : " + name + " TYPE : " + type + "");
                find(gen.toString(), name.toString(), type.toString());
                state=1;
//                Intent intent = new Intent(this,Shake.class);
//                startActivity(intent);
                break;
        }
    }
    public List<String> find(String gender , String creator , String type){
        Log.w("MyApp", "Find");

        List<Name> list = db.selectUser(gender,creator,type);
        names = new ArrayList<String>();
        for (Name name :list){
            names.add(name.getName());
        }
        return names;
    }
    public void set(){
        ArrayList<String> spinnerArray1 = new ArrayList<String>();
        spinnerArray1.add(0, "Бүх нэрс");
        spinnerArray1.add(1, "Миний нэрс");
        ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray1);
        name.setAdapter(spinnerArrayAdapter1);
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add(0, "Монгол");
        spinnerArray.add(1, "Модерн");
        spinnerArray.add(2, "Төвд");
        spinnerArray.add(3, "Түүхийн");
        spinnerArray.add(4, "Самгард");
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        type.setAdapter(spinnerArrayAdapter);
    }
    public String gender(){
        if(male.isChecked() == true)
            return "Male";
        else
            return "Female";
    }
    public String name(){
        Log.w("MyApp","NER = "+name.getSelectedItem().toString()+"");
        if(name.getSelectedItem().toString().equals("Бүх нэрс"))
            return "admin";
        else
            return "user";

    }
    public String type(){
        return  type.getSelectedItem().toString();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            //get x, y, z values
            float value[]=event.values;
            float x=value[0];
            float y=value[1];
            float z=value[2];
            //use the following formula
            //use gravity according to your place if you are on moon than use moon gravity
            float asr=(x*x+y*y+z*z)/(SensorManager.GRAVITY_EARTH*
                    SensorManager.GRAVITY_EARTH);
            //If mobile move any direction then the following condition will become true
            Log.w("MyApp","MoTIOn");
            if(asr>=2)
            {
                if(state ==1)
                {
                    result();
                }

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void result(){
            final EditText Comment = new EditText(this);
            int idx = new Random().nextInt(names.size());
            String random = (names.get(idx));
            state=0;
            Intent shake = new Intent();
            shake.setClassName("com.example.md.givename", "com.example.md.givename.Shake");
            shake.putExtra("name", String.valueOf(random));
            startActivity(shake);

    }
}
