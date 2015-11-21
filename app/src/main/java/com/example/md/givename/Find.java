package com.example.md.givename;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class Find extends AppCompatActivity {
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        db = new DatabaseHandler(this);
        check();
    }
    public void check(){
        int id =4;
        List<Name> names = db.getUserInformation(id);
        String value = null;
        for (Name cn : names) {
            value = "Id: " + cn.getId() + " -Name: " + cn.getName() + "-Comment: " + cn.getGender() + " -Gender: " + cn.getNational() + " -National: " + cn.getComment()
                    + "-Creator: " + cn.getCreator() + "";
            Toast toast = Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG);
            Log.w("MyApp",value);
            toast.show();
        }
    }
}
