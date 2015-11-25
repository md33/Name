package com.example.md.givename;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Edit extends AppCompatActivity {
    Spinner National, gender;
    DatabaseHandler db;
    String Thisname = null;
    EditText namee, comment;
    String genderr,national;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle user_information = getIntent().getExtras();
        Thisname = user_information.getString("name");
        National = (Spinner) findViewById(R.id.spinner2);
        gender = (Spinner) findViewById(R.id.spinner);
        db = new DatabaseHandler(this);
        namee = (EditText) findViewById(R.id.name);
        comment = (EditText) findViewById(R.id.comment);
        set_spinner();
        set_data();
    }

    public void set_spinner() {
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add(0, "Монгол");
        spinnerArray.add(1, "Модерн");
        spinnerArray.add(2, "Төвд");
        spinnerArray.add(3, "Түүхийн");
        spinnerArray.add(4, "Самгард");
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        National.setAdapter(spinnerArrayAdapter);
        ArrayList<String> spinnerArray1 = new ArrayList<String>();
        spinnerArray1.add(0, "Хүү");
        spinnerArray1.add(1, "Охин");

        ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        gender.setAdapter(spinnerArrayAdapter1);
    }

    public void set_data() {


        List<Name> name = db.getUserInformation(Thisname);
        for (Name cn : name) {
            Log.w("MyApp", "Name : " + cn.getName() + "---Comment : " + cn.getGender() + "---Gender : " + cn.getNational() + "---National : " + cn.getComment() + " ");
            namee.setText(cn.getName());
            national=cn.getComment();
            comment.setText(cn.getGender());
            genderr = cn.getNational();
            select();

        }

    }
    void select(){
        if(national.equals("Монгол"))
        {
                National.setSelection(0);
        }
        else if(national.equals("Модерн"))
        {
            National.setSelection(1);
        }else  if(national.equals("Төвд"))
        {
            National.setSelection(2);
        } else  if(national.equals("Түүхийн"))
        {
            National.setSelection(3);
        }else  if(national.equals("Самгард"))
        {
            National.setSelection(4);
        }
        if(genderr.equals("Хүү"))
        {
            gender.setSelection(0);
        }else if(genderr.equals("Охин")){
            gender.setSelection(1);
        }
    }
}
