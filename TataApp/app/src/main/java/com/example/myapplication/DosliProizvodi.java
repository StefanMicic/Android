package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DosliProizvodi  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="DosliProizvodi.db";

    public static final String TABLE_NAME="Tabela_proizvoda";

    public static final String COL_1="ID";
    public static final String COL_2="Naziv";
    public static final String COL_3="Kolicina";
    public static final String COL_4="Datum";

    public DosliProizvodi(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Naziv text, Kolicina int, Datum text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData (String n, int k,String d) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put(COL_2,n);
        temp.put(COL_3,k);
        temp.put(COL_4,d);
        if(db.insert(TABLE_NAME,null,temp)==-1)
            return false;
        else
            return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"Naziv=?",new String[] {id});
    }
    public Cursor getAllData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
