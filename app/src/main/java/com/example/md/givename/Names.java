package com.example.md.givename;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
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



public class Names extends Activity implements  SearchView.OnQueryTextListener{
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
    public ListView mine, all;
    public SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        db = new DatabaseHandler(this);
        mine = (ListView) findViewById(R.id.listView2);
        all = (ListView) findViewById(R.id.listView);
        mSearchView = (SearchView)findViewById(R.id.searchView);
        all.setTextFilterEnabled(true);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        db.InsertName(new Name(name, comment, gender, national, creator));
        db.InsertName(new Name("Дүвшин", "Гэгээн хувилгаан", "Хүү", "Төвд", "admin"));
        db.InsertName(new Name("Лхам", "Аз жаргалтай, баян дэлгэр амьдралыг бэлгэдэг", "Охин", "Төвд", "admin"));
        db.InsertName(new Name("Наряд", "Ажилын гүйцэтгэл гэсэн утгатай. ", "Хүү", "Төвд", "admin"));
        db.InsertName(new Name("Очир", " Хатуу ширүүн тэмцэгч гэсэн утгатай", "Хүү", "Төвд", "admin"));
        db.InsertName(new Name("Самбуу", "Сансан бүхэн нь биелэх ерөөлийг бэлгэддэг", "Хүү", "Төвд", "admin"));
        db.InsertName(new Name("Сүрэн", "Дайчин, шургуу гэсэн утгатай", "Хүү", "Төвд", "admin"));
        db.InsertName(new Name("Сосор", "Санаа сэтгэл гэсэн утгатай", "Хүү", "Төвд", "admin"));
        db.InsertName(new Name("Хайдав", "Их гэсэн үгийг илэрхийлдэг", "Хүү", "Төвд", "admin"));
        db.InsertName(new Name("Цэвэлмаа", "Тайван жаргалт эх гэсэн утгыг агуулна", "Охин", "Төвд", "admin"));
        db.InsertName(new Name("Цэрмаа", "Энэ нь зоригт дайчин эх гэсэн утгатай", "Охин", "Төвд", "admin"));
        db.InsertName(new Name("Цэрэн ", " голч, нэгэн үзүүр сэтгэлтэн гэсэн утгатай", "Охин", "Төвд", "admin"));
        db.InsertName(new Name("Цэнд-Аюуш", "нас уртсаж, өвчин эмгэгээс холуур, байж амьдрал нь өөдрөг явна гэсэн утгатай", "Хүү", "Төвд", "admin"));


        TabHost.TabSpec spec1 = tabHost.newTabSpec("TAB1");
        spec1.setContent(R.id.listView);
        spec1.setIndicator("Бүх нэрс");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("TAB2");
        spec2.setIndicator("Миний нэрс");
        spec2.setContent(R.id.listView2);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        check();
        setupSearchView();


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

                int named =  db.check_name(Names.getText().toString());
                Log.w("MyApp","Checkkk"+String.valueOf(name));

                if(name.equals("")&& comment.equals(""))
                {

                    Toast toast = Toast.makeText(getApplicationContext(), "Дутуу бөглөсөн байна", Toast.LENGTH_LONG);

                    toast.show();

                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "insert", Toast.LENGTH_LONG);
                    toast.show();
//                    insert();
                    check();
                }
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

//    public void insert() {
//        try {
//            db.InsertName(new Name(name, comment, gender, national, creator));
//            Log.w("MyApp",name+ comment + gender + national+creator);
//
//        } catch (Exception ex) {
//            Log.w("MyApp","ada"+ ex.toString());
//            Toast toast = Toast.makeText(getApplicationContext(), "Амжилттай нэмэгдлээ", Toast.LENGTH_LONG);
//            toast.show();
//        }
//    }
    public String check_gender() {
        String gender1=null;
        if(Male.isChecked() != false || Female.isChecked() !=false) {
            if (Male.isChecked() == true)
                gender1 = "Хүү";
            else
                gender1 = "Охин";
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Дутуу бөглөсөн байна", Toast.LENGTH_LONG);

            toast.show();
        }
        return gender1;
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
                TextView c = (TextView) view.findViewById(R.id.pp);
                String named = c.getText().toString();
                Intent intent = new Intent();
                intent.setClassName("com.example.md.givename", "com.example.md.givename.View");
                intent.putExtra("name", named);
                intent.putExtra("activity", "NAMES");
                startActivity(intent);
            }
        });
        Cursor cursor1 = db.getDetail();

        if (cursor1 != null)

            adapter =
                    new SimpleCursorAdapter(this,
                            R.layout.row,
                            cursor,
                            new String[]{
                                    "NAME", "GENDER"
                            },
                            new int[]{R.id.pp, R.id.dd},
                            1);


        mine.setAdapter(adapter);

        mine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView c = (TextView) view.findViewById(R.id.pp);
                String named = c.getText().toString();
                Intent intent = new Intent();
                intent.setClassName("com.example.md.givename", "com.example.md.givename.View");
                intent.putExtra("name", named);
                intent.putExtra("activity", "NAMES");
                startActivity(intent);
            }
        });

    }
//    public void check(){
//        String[] from = new String[] {"ID","NAME", "GENDER"};
//        int[] to = new int[] {R.id.id, R.id.pp, R.id.dd };
//        final List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
//        List<Name>xxa=db.all();
//        for(Name cd :xxa) {
//
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("ID", String.valueOf(cd.getId()));
//            map.put("NAME", cd.getName());
//            map.put("GENDER", cd.getNational());
//
//            fillMaps.add(map);
//
//        }
//        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.row, from, to);
//        all.setAdapter(adapter);
//        all.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast toast = Toast.makeText(getApplicationContext(), parent.toString() + view.toString() + String.valueOf(position)+String.valueOf(id), Toast.LENGTH_LONG);
//                    toast.show();
//
//
//
//            }
//        });
//    }
    private void setupSearchView() {
        try {


            mSearchView.setIconifiedByDefault(false);
            mSearchView.setOnQueryTextListener(this);
            mSearchView.setSubmitButtonEnabled(true);
            mSearchView.setQueryHint("Хайх...");
        }
        catch (Exception ex)
        {
            Log.i("MyApp",ex.toString());
        }
}

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            all.clearTextFilter();
        } else {
            all.setFilterText(newText.toString());
        }
        return true;
    }
}
