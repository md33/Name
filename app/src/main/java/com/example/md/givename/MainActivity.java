package com.example.md.givename;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.find:
                Intent find = new Intent(this,Find.class);
                startActivity(find);
                break;
            case R.id.name:
                Intent names = new Intent(this,Names.class);
                startActivity(names);
                break;
            case R.id.exit:
                System.exit(1);
                break;
        }
    }
}
