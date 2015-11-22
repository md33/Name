package com.example.md.givename;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Find extends AppCompatActivity {
    DatabaseHandler db;
    Spinner name , type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        db = new DatabaseHandler(this);
        name =(Spinner)findViewById(R.id.ner);
        type = (Spinner)findViewById(R.id.type);
        set();
    }
    public void onClick(android.view.View view){
        switch (view.getId())
        {
            case R.id.begin:
                Log.w("MyApp","Begin");
                find("Male","admin","Монгол");
                break;
        }
    }
    public void find(String gender , String creator , String type){
        Log.w("MyApp","Find");

        List<Name> list = db.selectUser(gender,creator,type);
        ArrayList<String>names = new ArrayList<String>();
        for (Name name :list){
            names.add(name.getName());
        }
        Log.w("MyApp",names.toString());
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
}
