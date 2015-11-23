package com.example.md.givename;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class View extends AppCompatActivity {
    public String ThisName;
    DatabaseHandler db = null;
    EditText Name , comment ;
    RadioButton male, female;
    Spinner National;
    Button change;
    int oorchllt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Bundle user_information = getIntent().getExtras();
        ThisName = user_information.getString("id");
        db = new DatabaseHandler(this);
        Name = (EditText)findViewById(R.id.name);
        comment = (EditText)findViewById(R.id.comment);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        National = (Spinner)findViewById(R.id.spinner);
        change = (Button)findViewById(R.id.change);
        check();
        Log.w("MyApp", "1   id = " + ThisName);


    }
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.change:
                String name =change.getText().toString();
                Toast toast = Toast.makeText(getApplicationContext(), "XAXA", Toast.LENGTH_LONG);
                    toast.show();
                if(name.equals("Өөрчлөх")) {
                    oorchllt = 1;
                    Toast toast1 = Toast.makeText(getApplicationContext(), "XEXE", Toast.LENGTH_LONG);
                    toast1.show();
                    check();
                    change.setText("Хадгалах");
                }
                else {

                }
                break;
            case R.id.delete:

            case R.id.back:
                finish();
                break;
        }
    }
    public void set_data() {
        String gender,national;
        int id ;
        id = Integer.parseInt(ThisName)+1;
        List<Name> name = db.getUserInformation(id);
        for (Name cn : name) {
            Log.w("MyApp","Name : "+cn.getName()+"---Comment : "+cn.getGender()+"---Gender : "+cn.getNational()+"---National : "+cn.getComment()+" ");
            Name.setText(cn.getName());
            comment.setText(cn.getGender());
            gender = cn.getNational();
            if (gender.matches("Male")) {
                male.setChecked(true);
                female.setChecked(false);
            } else {
                female.setChecked(true);
                male.setChecked(false);
            }
            national = cn.getComment();
            setNational(national);
        }
    }
    public  void setNational(String national){
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
        if(national.equals("Монгол"))
        {
            National.setSelection(0);
        }
        else
            if(national.equals("Модерн"))
            {
                National.setSelection(1);
            }
            else
            if(national.equals("Төвд"))
            {
                National.setSelection(2);
            }
            else
            if(national.equals("Түүхийн"))
            {
                National.setSelection(3);
            }
            else
            if(national.equals("Самгард"))
            {
                National.setSelection(4);
            }

    }
    public void check(){
        if(oorchllt == 0)
        {
            Name.setEnabled(false);
            comment.setEnabled(false);
            male.setEnabled(false);
            female.setEnabled(false);
            National.setEnabled(false);
            set_data();
        }
        else {
            Name.setEnabled(true);
            comment.setEnabled(true);
            male.setEnabled(true);
            female.setEnabled(true);
            National.setEnabled(true);
            set_data();
        }
    }
}
