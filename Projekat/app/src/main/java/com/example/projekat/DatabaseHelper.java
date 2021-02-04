package com.example.projekat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Baza12.db";

    public static final String TABLE_NAME="Tabela_korisnika";

    public static final String COL_1="ID";
    public static final String COL_2="Username";
    public static final String COL_3="Lozinka";
    public static final String COL_4="Prodavac";
    public static final String COL_5="Adresa";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Username text UNIQUE, Lozinka text, Prodavac text ,Adresa text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData (String username, String lozinka,String adresa) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put(COL_2,username);
        temp.put(COL_3,lozinka);
        temp.put(COL_4,"ne");
        temp.put(COL_5,adresa);
        if(db.insert(TABLE_NAME,null,temp)==-1)
            return false;
        else
            return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[] {id});
    }
    public Cursor getAllData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

}
