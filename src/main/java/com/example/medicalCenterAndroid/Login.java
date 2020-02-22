package com.example.milicavorkapic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    BazaKorisnika myDb;
    Button Login,registracija ;
    EditText ime , lozinka;
    static String loggedUser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new BazaKorisnika(this);
        Login = (Button) findViewById(R.id.Login);
        ime = (EditText) findViewById(R.id.ime);
        lozinka= (EditText) findViewById(R.id.lozinka);
        registracija = (Button) findViewById(R.id.reg);
        registracija.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       openRegistracija();
                    }
                }
        );
        Login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prijavaKorisnika();
                    }
                }
        );
    }

    public void openRegistracija(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void openDoktor(){
        Intent intent = new Intent(this,Doktor.class);
        startActivity(intent);
    }
    public void openPacijent(){
        Intent intent = new Intent(this,Pacijent.class);
        startActivity(intent);
    }
    public void prijavaKorisnika(){
        Cursor res= myDb.getAllData();
        while(res.moveToNext()){
           if(res.getString(1).equals(ime.getText().toString())&&res.getString(2).equals(lozinka.getText().toString())){
                if(res.getString(4).toLowerCase().equals("doktor")){
                    openDoktor();
                    loggedUser=res.getString(1);
                    Toast.makeText(Login.this,"Uspesna prijava doktora",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Login.this, "Uspesna prijava pacijenta", Toast.LENGTH_LONG).show();
                    loggedUser=res.getString(1);
                    openPacijent();
                }
                return;
            }
        }
            Toast.makeText(Login.this,"Neuspesna prijava",Toast.LENGTH_LONG).show();
            return;

    }
}
