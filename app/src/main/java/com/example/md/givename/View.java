package com.example.md.givename;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class View extends Activity {
    public String ThisName;
    DatabaseHandler db = null;
    ImageView gender;
    TextView vname , vnational, vcomment;
    String user , activity;
    ImageButton remove ,edit;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Bundle user_information = getIntent().getExtras();
        ThisName = user_information.getString("name");
        activity = user_information.getString("activity");

        db = new DatabaseHandler(this);
        vname = (TextView)findViewById(R.id.name);
        vnational = (TextView)findViewById(R.id.national);
        vcomment = (TextView)findViewById(R.id.comment);
        gender = (ImageView)findViewById(R.id.imageView);
        remove = (ImageButton)findViewById(R.id.remove);
        edit = (ImageButton)findViewById(R.id.edit);
        set_data();



    }
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.remove:
                delete();
                break;
            case R.id.edit:
                Intent intent = new Intent();
                intent.setClassName("com.example.md.givename", "com.example.md.givename.Edit");
                intent.putExtra("name", vname.getText().toString());
                startActivity(intent);
                break;

        }
    }
    public void set_data() {
        if(activity.equals("FIND")) {
            edit.setVisibility(android.view.View.INVISIBLE);
            remove.setVisibility(android.view.View.INVISIBLE);
        }
        String genderr;

        List<Name> name = db.getUserInformation(ThisName);
        for (Name cn : name) {
            Log.w("MyApp", "Name : " + cn.getName() + "---Comment : " + cn.getGender() + "---Gender : " + cn.getNational() + "---National : " + cn.getComment() + " ");
            vname.setText(cn.getName());
            vnational.setText(cn.getComment());
            vcomment.setText(cn.getGender());
            genderr = cn.getNational();
            Log.w("MyAp","gender"+cn.getNational());
            if (genderr.matches("Хүү")) {
                gender.setImageResource(R.drawable.male);
            } else {
                gender.setImageResource(R.drawable.female);
            }
            user = cn.getCreator();
            id= cn.getId();
            if(user.equals("user"))
            {
                edit.setVisibility(android.view.View.VISIBLE);
                remove.setVisibility(android.view.View.VISIBLE);
            }
//            else
//            {
//                edit.setVisibility(android.view.View.INVISIBLE);
//                remove.setVisibility(android.view.View.INVISIBLE);
//            }

        }
    }
    public void delete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Нэр устгах");
        final TextView Names = new TextView(this);
        Names.setText("Энэ нэрийг устгах уу ??");
        builder.setView(Names);
        builder.setPositiveButton("Тийм", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    db.DeleteName(id);

                } catch (Exception ex) {
                    Log.w("MyApp", "ada" + ex.toString());
                }
                finish();
                Toast toast = Toast.makeText(getApplicationContext(), "Амжилттай устгалаа", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        builder.setNegativeButton("Үгүй", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                back();
            }
        });
        builder.show();
    }
    public void back() {
        Intent intent = new Intent();
        intent.setClassName("com.example.md.givename", "com.example.md.givename.Names");
        startActivity(intent);
    }

}
