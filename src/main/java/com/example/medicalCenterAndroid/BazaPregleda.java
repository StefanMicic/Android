package com.example.milicavorkapic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BazaPregleda extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="BazaPregleda1.db";
    public static final String TABLE_NAME="Apps";

    public static final String COL_1="Naslov";
    public static final String COL_2="Datum";
    public static final String COL_3="Vreme";
    public static final String COL_4="Pacijent";
    public static final String COL_5 = "Doktor";


    public BazaPregleda(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(Naslov text PRIMARY KEY UNIQUE, " +
                "Datum text, " +
                "Vreme text," +
                "Pacijent text, " +
                "Doktor text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData (String naslov, String datum,int vreme,String pacijent,String doktor) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put(COL_1,naslov);
        temp.put(COL_2,datum);
        temp.put(COL_3,vreme);
        temp.put(COL_4,pacijent);
        temp.put(COL_5,doktor);
        if(db.insert(TABLE_NAME,null,temp)==-1)
            return false;
        else
            return true;
    }
    public boolean updateProduct(String name, String date,String time,String doctor,String patient) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put(COL_1,name);
        temp.put(COL_2,date);
        temp.put(COL_3,time);
        temp.put(COL_5,doctor);
        temp.put(COL_4,patient);

        db.update(TABLE_NAME,temp,"Naslov=?",new String[]{name});
        return true;

    }
    public Integer deleteDataByName(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"Naslov=?",new String[] {name});
    }
    public Cursor getByName(String naslov){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where Naslov="+naslov,null);
        return res;
    }
    public Cursor getAllData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
