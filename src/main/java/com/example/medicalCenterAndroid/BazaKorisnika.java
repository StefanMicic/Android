package com.example.milicavorkapic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BazaKorisnika extends SQLiteOpenHelper {
        public static final String DATABASE_NAME="Baza12.db";

        public static final String TABLE_NAME="Users";

        public static final String COL_1="ID";
        public static final String COL_2="Ime";
        public static final String COL_3="Lozinka";
        public static final String COL_4="JMBG";
        public static final String COL_5 = "Uloga";


    public BazaKorisnika(@Nullable Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " Ime text UNIQUE, Lozinka text, JMBG INTEGER ,Uloga text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(db);
        }
        public boolean insertData (String ime, String lozinka,int jmbg,String uloga) {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues temp = new ContentValues();
            temp.put(COL_2,ime);
            temp.put(COL_3,lozinka);
            temp.put(COL_4,jmbg);
            temp.put(COL_5,uloga);
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
