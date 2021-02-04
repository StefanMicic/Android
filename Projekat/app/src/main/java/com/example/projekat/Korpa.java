package com.example.projekat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Korpa extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Korpa1.db";
    public static final String TB_NAME="Tabela_proizvoda";

    public static final String COL_1="Naziv";
    public static final String COL_2="Cena";
    public static final String COL_3="Kolicina";
    public static final String COL_4="Kupac";
    public Korpa(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TB_NAME + "(Naziv text PRIMARY KEY ," +
                " Cena INTEGER, Kolicina  INTEGER,Kupac text )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TB_NAME);
        onCreate(db);
    }
    public boolean updateProduct(String name, String price,String quantity,String  kupac) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put(COL_1,name);
        temp.put(COL_2,price);
        temp.put(COL_3,quantity);
        temp.put(COL_4,kupac);
        db.update(TB_NAME,temp,"Naziv=?",new String[]{name});
        return true;

    }
    public boolean insertProduct(String name, String price,String quantity,String kupac) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put(COL_1,name);
        temp.put(COL_2,price);
        temp.put(COL_3,quantity);
        temp.put(COL_4,kupac);
        if(db.insert(TB_NAME,null,temp)==-1)
            return false;
        else
            return true;
    }
    public Cursor getAllProduct(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TB_NAME,null);
        return res;
    }
    public void deleteValues(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TB_NAME,"Kupac=?",new String[] {name});
    }
    public void deleteProduct(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TB_NAME,"Naziv=?",new String[] {name});
    }
}
