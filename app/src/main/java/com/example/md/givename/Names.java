package com.example.md.givename;

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
import android.widget.Toast;

import org.w3c.dom.NameList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class Names extends AppCompatActivity {
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
    public SimpleCursorAdapter dataAdapter;

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
        spinnerArray.add(3, "Түүгийн");
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
                insert();
//                main();
                check();
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
            Log.w("MyApp", ex.toString());
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
        List<String> list1 = null;
//        try {
//            Cursor cursor = db.ALL();
//            String[] row = new String[]{db.NAME};
//            int[] nu = new int[]{R.id.plus};
//            SimpleCursorAdapter simpleCursorAdapter;
//            simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.row, cursor, row, nu, 0);
//            all.setAdapter(simpleCursorAdapter);
//        } catch (Exception ex) {
//            Log.w("MyApp", ex.toString());
//        }
        try {
            List<Name> nameList = db.AllName();
            String value = null;

            int id = 0, i = 0;
            ArrayList<String> list = null;

            for (Name cn : nameList) {
                id = cn.getId();
            }
            Log.w("MyApp", "id = " + id + "");
            String[] s = new String[id];
            for (Name cn : nameList) {

                s[i] = cn.getName();
                i++;

                list1 = Arrays.asList(s);

                Log.w("MyApp", "LIST" + list1.toString());


                value = "VALUEEEE Id: " + cn.getId() + " -Name: " + cn.getName() + "-Comment: " + cn.getGender() + " -Gender: " + cn.getNational() + " -National: " + cn.getComment()
                        + "-Creator: " + cn.getCreator() + "";
                Toast toast = Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG);
                Log.w("MyApp", value);
                toast.show();
            }
        }
        catch (Exception ex){
            Log.w("MyApp",ex.toString());
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list1);
        all.setAdapter(adapter);
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
    }
}
