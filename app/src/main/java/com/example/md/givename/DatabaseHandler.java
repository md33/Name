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
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GIVENAME";
    private static final String TABLE_NAME = "NAMES";
    private static final String NAME_ID = "ID";
    private static final String NAME = "NAME";
    private static final String COMMENT = "COMMENT";
    private static final String GENDER = "GENDER";
    private static final String NATIONAL = "NATIONAL";
    private static final String CREATOR = "CREATOR";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT,"
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
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }
    public int UpdateName(Name name,int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name.getName());
        contentValues.put(COMMENT,name.getComment());
        contentValues.put(GENDER,name.getGender());
        contentValues.put(NATIONAL, name.getNational());
        contentValues.put(CREATOR, name.getCreator());
        return db.update(DATABASE_NAME,contentValues,NAME_ID + "=" + id , null);
    }
    public int DeleteName(Name name ,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(DATABASE_NAME, NAME_ID + "=" + id, null);
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
//                    name.setId(Integer.parseInt(cursor.getString(0)));
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
    public Cursor ALL(){
        Cursor cursor = null;
        try{
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+"";
         cursor= db.rawQuery(query,null);
        }
        catch (Exception ex)
        {
            Log.w("MyApp",ex.toString());
        }
        return  cursor;
    }
    public List<Name> getUserInformation(int id) {
        List<Name> nameList = new ArrayList<Name>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ID = '"+id+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
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
}
