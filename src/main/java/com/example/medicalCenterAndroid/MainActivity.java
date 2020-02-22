package com.example.milicavorkapic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    BazaKorisnika myDb;
    EditText username, password, jmbg,uloga;
    Button registracija;
    Button view ;
    Button Login ;
    public static String prijavljeni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new BazaKorisnika(this);
        jmbg= findViewById(R.id.jmbg);
        uloga=findViewById(R.id.uloga);
        username = (EditText) findViewById(R.id.ime);
        password = (EditText) findViewById(R.id.lozinka);
        registracija = (Button) findViewById(R.id.reg);
        Login = (Button) findViewById(R.id.Log);
        RegistracijaKorisnika();

        Login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openPrijava();
                    }
                }
        );
    }
    public void openPrijava(){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
    public void RegistracijaKorisnika(){
        registracija.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(username.getText().toString().trim().isEmpty() ||
                                jmbg.getText().toString().trim().isEmpty() ||
                                password.getText().toString().trim().isEmpty() ||
                                uloga.getText().toString().trim().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Polja ne smeju biti prazna", Toast.LENGTH_LONG).show();
                            return;
                        }else if(!uloga.getText().toString().toLowerCase().equals("doktor") &&
                                !uloga.getText().toString().toLowerCase().equals("pacijent") ){
                            Toast.makeText(MainActivity.this, "Uloga moze biti pacijent ili doktor", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(myDb.insertData(username.getText().toString(), password.getText().toString(),Integer.parseInt(jmbg.getText().toString()),
                                uloga.getText().toString()))
                            Toast.makeText(MainActivity.this,"Uspesna registracija",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Neuspesna registracija",Toast.LENGTH_LONG).show();
                    prijavljeni=username.getText().toString();
                    }
                }
        );
    }

}
