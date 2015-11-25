package com.example.md.givename;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
    public ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        imageView = (ImageView)findViewById(R.id.imageView);
//        imageView.setImageResource(R.drawable.a);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.find:
                Intent find = new Intent(this,Find.class);
                startActivity(find);
                break;
            case R.id.gender:
                Intent names = new Intent(this,Names.class);
                startActivity(names);
                break;
            case R.id.exit:
                System.exit(1);
                break;
        }
    }
}
