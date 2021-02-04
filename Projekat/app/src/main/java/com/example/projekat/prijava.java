package com.example.projekat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class prijava extends AppCompatActivity {

    private SlidrInterface slidr;
    DatabaseHelper myDb;
    Button Login ;
    EditText ime , lozinka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prijava);
        myDb = new DatabaseHelper(this);
       slidr= Slidr.attach(this);
       slidr.unlock();
       Login = (Button) findViewById(R.id.button);
       ime = (EditText) findViewById(R.id.ime);
       lozinka= (EditText) findViewById(R.id.lozinka);
       Login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prijavaKorisnika();
                    }
                }
        );
    }
    public void openProdavac(){
        Intent intent = new Intent(this,ListaProizvoda.class);
        startActivity(intent);
    }
    public void openKUpac(){
        Intent intent = new Intent(this,Kupac.class);
        startActivity(intent);
    }
    public void prijavaKorisnika(){

        Cursor res= myDb.getAllData();
        boolean goodUserPass = false;
        boolean isKorisnik=true;
        while(res.moveToNext()){
            if(res.getString(1).equals(ime.getText().toString())&&res.getString(2).equals(lozinka.getText().toString())){
                goodUserPass=true;
                if(res.getString(3).toString().equals("ne"))
                    isKorisnik=false;
                break;
            }
        }
        if(goodUserPass){
            MainActivity.prijavljeni=ime.getText().toString();
        }
        else{
            Toast.makeText(prijava.this,"Neuspesna prijava",Toast.LENGTH_LONG).show();
            return;
        }
        if(isKorisnik==true)
            openProdavac();
        else
            openKUpac();
    }

}
