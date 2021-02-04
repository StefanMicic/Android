package com.example.projekat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dataProduct extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="ProductBase.db";
    public static final String TB_NAME="Tabela_proizvoda";

    public static final String COL_1="Naziv";
    public static final String COL_2="Cena";
    public static final String COL_3="Kolicina";
    public dataProduct(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TB_NAME + "(Naziv text PRIMARY KEY ," +
                " Cena INTEGER, Kolicina  INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TB_NAME);
        onCreate(db);


    }
    public boolean insertProduct(String name, String price,String quantity) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put(COL_1,name);
        temp.put(COL_2,price);
        temp.put(COL_3,quantity);
        if(db.insert(TB_NAME,null,temp)==-1)
            return false;
        else
            return true;

    }
    public boolean updateProduct(String name, String price,String quantity) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues temp = new ContentValues();
        temp.put(COL_1,name);
        temp.put(COL_2,price);
        temp.put(COL_3,quantity);
        db.update(TB_NAME,temp,"Naziv=?",new String[]{name});
        return true;

    }
    public Integer deleteData(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TB_NAME,"Naziv=?",new String[] {name});
    }
    public Cursor getAllProduct(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TB_NAME,null);
        return res;
    }
}
