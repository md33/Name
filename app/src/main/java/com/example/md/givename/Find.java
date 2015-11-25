package com.example.md.givename;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Find extends Activity implements SensorEventListener{
    SensorManager sm;
    public DatabaseHandler db;
    public Spinner name , type,gender;
    public TextView ner , torol , hvis;

    public  ArrayList<String>names;
    public ImageView imageView;
    public int state  = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        db = new DatabaseHandler(this);
        name =(Spinner)findViewById(R.id.name);
        type = (Spinner)findViewById(R.id.comment);
        gender = (Spinner)findViewById(R.id.gender);
        ner = (TextView)findViewById(R.id.ner);
        torol = (TextView)findViewById(R.id.torol);
        hvis = (TextView)findViewById(R.id.hvis);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.findbabename);


        sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        set();

    }
    public void onClick(android.view.View view){
        switch (view.getId())
        {
            case R.id.button:
                String gen = null,name =null ,type=null;
                gen = gender();
                name=name();
                type=type();
                Log.w("MyApp", "ASD  GENDER : " + gen + " NAME : " + name + " TYPE : " + type + "");
                find(gen.toString(), name.toString(), type.toString());
                state=1;
                Toast toast = Toast.makeText(getApplicationContext(),"Ta утсаа сэгсэрнэ үү !", Toast.LENGTH_SHORT);
                    toast.show();
                Intent intent = new Intent(this,Shake.class);
                startActivity(intent);

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
        Log.w("MyApp","Gender : " + gender + "CREATOR : "+creator +"TYpe : "+type+ names.toString());
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
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        type.setAdapter(spinnerArrayAdapter2);
        ArrayList<String> spinnerArray3 = new ArrayList<String>();
        spinnerArray3.add(0, "Хүү");
        spinnerArray3.add(1, "Охин");
        ArrayAdapter spinnerArrayAdapter3 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray3);
        gender.setAdapter(spinnerArrayAdapter3);

    }
    public String name(){
        Log.w("MyApp","NER = "+name.getSelectedItem().toString()+"");
        if(name.getSelectedItem().toString().equals("Бүх нэрс"))
            return "admin";
        else
            return "user";
    }
    public String gender(){
        if(gender.getSelectedItem().toString().equals("Хүү"))
            return "Male";
        else
            return "Female";
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
        state = 0;
        Intent shake = new Intent();
            shake.setClassName("com.example.md.givename", "com.example.md.givename.View");
            shake.putExtra("name", String.valueOf(random));
            Log.w("MyApp","random"+random.toString());
            startActivity(shake);

    }
}
