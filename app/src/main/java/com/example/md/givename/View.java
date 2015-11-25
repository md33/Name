package com.example.md.givename;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class View extends Activity {
    public String ThisName;
    DatabaseHandler db = null;
    ImageView gender;
    TextView vname , vnational, vcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Bundle user_information = getIntent().getExtras();
        ThisName = user_information.getString("name");
        Log.w("MyApp", "1   id = " + ThisName);
        db = new DatabaseHandler(this);
        vname = (TextView)findViewById(R.id.name);
        vnational = (TextView)findViewById(R.id.national);
        vcomment = (TextView)findViewById(R.id.comment);
        gender = (ImageView)findViewById(R.id.imageView);
        set_data();



    }
    public void onClick(android.view.View view) {
        switch (view.getId()) {
        }
    }
    public void set_data() {
        String genderr;
        int id ;
        List<Name> name = db.getUserInformation(ThisName);
        for (Name cn : name) {
            Log.w("MyApp","Name : "+cn.getName()+"---Comment : "+cn.getGender()+"---Gender : "+cn.getNational()+"---National : "+cn.getComment()+" ");
            vname.setText(cn.getName());
            vnational.setText(cn.getComment());
            vcomment.setText(cn.getGender());
            genderr = cn.getNational();
            if (genderr.matches("Male")) {
                gender.setImageResource(R.drawable.male);
            } else {
                gender.setImageResource(R.drawable.female);
            }

        }
    }
}
