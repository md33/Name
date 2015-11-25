package com.example.md.givename;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GIVENAME";
    public static final String TABLE_NAME = "NAMES";
    public static final String NAME_ID = "ID";
    public static final String NAME = "NAME";
    public static final String COMMENT = "COMMENT";
    public static final String GENDER = "GENDER";
    public static final String NATIONAL = "NATIONAL";
    public static final String CREATOR = "CREATOR";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " UNIQUE,"
                + COMMENT + " TEXT,"
                + GENDER + " TEXT,"
                + NATIONAL + " TEXT,"
                + CREATOR + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void InsertName(Name name){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name.getName());
        contentValues.put(COMMENT,name.getComment());
        contentValues.put(GENDER,name.getGender());
        contentValues.put(NATIONAL,name.getNational());
        contentValues.put(CREATOR,name.getCreator());
        Log.w("MyApp",contentValues.toString());
        db.insert(TABLE_NAME, null, contentValues);

        db.close();
    }
    public int UpdateName(Name name,int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name.getName());
        contentValues.put(COMMENT,name.getComment());
        contentValues.put(GENDER, name.getGender());
        contentValues.put(NATIONAL, name.getNational());
        contentValues.put(CREATOR, name.getCreator());
        return db.update(TABLE_NAME,contentValues,NAME_ID + "=" + id , null);
    }
    public int DeleteName(  int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME, NAME_ID + "=" + id, null);
    }
    public List<Name> AllName(){
        List<Name> nameList = new ArrayList<Name>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            String query = "SELECT * FROM "+TABLE_NAME+"";
            Cursor cursor= db.rawQuery(query,null);
            if(cursor.moveToFirst()) {
                do {
                    Name name = new Name();
                    name.setId(Integer.parseInt(cursor.getString(0)));
                    name.setName(cursor.getString(1));
                    name.setComment(cursor.getString(2));
                    name.setGender(cursor.getString(3));
                    name.setNational(cursor.getString(4));
                    name.setCreator(cursor.getString(5));
                    nameList.add(name);
                } while (cursor.moveToNext());
            }
            return nameList;
        }
        catch (Exception ex)
        {
            Log.w("MyApp", ex.toString());
        }
        db.close();
        return nameList;
    }


        public Cursor getDetails()
        {
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery("select "+NAME_ID+" as _id ,"+NAME+" , "+GENDER+" from "+TABLE_NAME+"", null);
        }
    public Cursor getDetail()
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select "+NAME_ID+" as _id ,"+NAME+" , "+GENDER+" from "+TABLE_NAME+" WHERE "+CREATOR+" = 'user' ", null);
    }

    public List<Name> selectUser(String gender, String creator, String national) {
        Log.w("MyApp", "DB");
        List<Name> nameList = new ArrayList<Name>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE GENDER = '" + gender + "' AND CREATOR = '" + creator + "' AND NATIONAL = '" + national+"'";
            Cursor cursor = db.rawQuery(query,null);
            Log.w("MyApp",cursor.toString());
            if(cursor.moveToFirst()){
                do{
                    Name name = new Name();
                    name.setId(Integer.parseInt(cursor.getString(0)));
                    name.setName(cursor.getString(1));

                    name.setGender(cursor.getString(2));
                    name.setNational(cursor.getString(3));
                    name.setComment(cursor.getString(4));
                    name.setCreator(cursor.getString(5));
                    nameList.add(name);
                }while (cursor.moveToNext());
            }
            return nameList;
        }
        catch (Exception ex)
        {
            Log.w("MyApp", ex.toString());
        }
        Log.w("MyApp", nameList.toString());
        return nameList;

    }
    public List<Name> getUserInformation(String named) {
        List<Name> nameList = new ArrayList<Name>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = '"+named+"'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Name name = new Name();
                    name.setId(Integer.parseInt(cursor.getString(0)));
                    name.setName(cursor.getString(1));
                    name.setGender(cursor.getString(2));
                    name.setNational(cursor.getString(3));
                    name.setComment(cursor.getString(4));
                    name.setCreator(cursor.getString(5));
                    nameList.add(name);
                } while (cursor.moveToNext());
            }
            return nameList;
        } catch (Exception ex) {
            Log.v("MyApp", ex.getMessage());
        }
        db.close();
        return nameList;
    }
    public int check_name(String name)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = '"+name+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = (Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());

        }
        if(id!=0)
        {
            Log.i("MyApp", "okey");
        }
        return id;
    }
    public List<Name> all( ) {
        List<Name> nameList = new ArrayList<Name>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String query = "SELECT * FROM " + TABLE_NAME + "";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Name name = new Name();
                    name.setId(Integer.parseInt(cursor.getString(0)));
                    name.setName(cursor.getString(1));
                    name.setGender(cursor.getString(3));
                    nameList.add(name);
                } while (cursor.moveToNext());
            }
            return nameList;
        } catch (Exception ex) {
            Log.v("MyApp", ex.getMessage());
        }
        db.close();
        return nameList;
    }
}
