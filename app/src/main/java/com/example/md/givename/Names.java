package com.example.md.givename;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.NameList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class Names extends Activity {
    public TabHost tabHost;
    public String name = null;
    public String comment = null;
    public String gender = null;
    public String national = null;
    public String creator = "admin";
    final Context context = this;
    public Spinner dropdown;
    public DatabaseHandler db = null;
    public RadioButton Male;
    public RadioButton Female;
    public SimpleCursorAdapter adapter;
    public List<String> list1 = null;
    ListView mine, all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        db = new DatabaseHandler(this);
        mine = (ListView) findViewById(R.id.listView2);
        all = (ListView) findViewById(R.id.listView);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("TAB1");
        spec1.setContent(R.id.listView);
        spec1.setIndicator("Бүх нэрс");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("TAB2");
        spec2.setIndicator("Миний нэрс");
        spec2.setContent(R.id.listView2);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        check();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert:
                insertFrom();
                break;

        }
    }

    public void insertFrom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Нэр нэмэх");

        final EditText Names = new EditText(this);
        Names.setInputType(InputType.TYPE_CLASS_TEXT);
        Names.setHint("Нэр");

        final EditText Comment = new EditText(this);
        Comment.setInputType(InputType.TYPE_CLASS_TEXT);
        Comment.setHint("Тайлбар");

        RadioGroup group = new RadioGroup(this);
        Male = new RadioButton(this);
        Male.setText("Хүү");

        group.addView(Male);

        Female = new RadioButton(this);
        Female.setText("Охин");
        group.addView(Female);

        final Spinner National = new Spinner(this);


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

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(Names);
        layout.addView(Comment);
        layout.addView(group);
        layout.addView(National);
        builder.setView(layout);
        builder.setPositiveButton("Нэмэх", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = Names.getText().toString();
                comment = Comment.getText().toString();
                gender = check_gender();
                national = National.getSelectedItem().toString();

                int name =  db.check_name(Names.getText().toString());
                Log.w("MyApp","Checkkk"+String.valueOf(name));
//                if(name != 0) {
                    insert();
//                main();
//                    check();
//                }
//                else
//                {
//                    Toast toast = Toast.makeText(getApplicationContext(), "Энэ нэр байгаа учир" +
//                            " нэмэх боломжгүй", Toast.LENGTH_LONG);
//                    toast.show();
//                }
            }
        });
        builder.setNegativeButton("Цуцлах", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();

    }

    public void insert() {
        try {
            db.InsertName(new Name(name, comment, gender, national, creator));
            Toast toast = Toast.makeText(getApplicationContext(), "Амжилттай нэмэгдлээ", Toast.LENGTH_LONG);
            toast.show();
        } catch (Exception ex) {
            Log.w("MyApp","ada"+ ex.toString());
        }
    }
    public String check_gender() {
        if (Male.isChecked() == true)
            return "Male";
        else
            return "Female";
    }
    public void main() {
        Intent intent = new Intent();
        intent.setClassName("com.example.md.givename", "com.example.md.givename.MainActivity");
        startActivity(intent);
    }
    public void check() {

        Cursor cursor = db.getDetails();

        if (cursor != null)

            adapter =
                    new SimpleCursorAdapter(this,
                            R.layout.row,
                            cursor,
                            new String[]{
                                    "NAME", "GENDER"
                            },
                            new int[]{R.id.pp, R.id.dd},
                            1);


        all.setAdapter(adapter);

        all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent();
                intent.setClassName("com.example.md.givename", "com.example.md.givename.View");
                Log.w("MyApp", "Parent" + parent.toString());
                Log.w("MyApp", "position"+ String.valueOf(position));
//                all.selected

                Log.w("MyApp", "id"+ "");
                Log.w("MyApp","");
//                intent.putExtra("id", String.valueOf(position));
//                startActivity(intent);
            }
        });


    }
}
