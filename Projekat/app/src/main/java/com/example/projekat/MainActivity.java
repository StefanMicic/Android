package com.example.projekat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText username, password, adresa;
    Button registracija;
    Button view ;
    Button Login ;
    public static String prijavljeni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);
        adresa= findViewById(R.id.adresa);
        username = (EditText) findViewById(R.id.ime);
        password = (EditText) findViewById(R.id.prezime);
        registracija = (Button) findViewById(R.id.reg);
        Login = (Button) findViewById(R.id.Log);
        RegistracijaKorisnika();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        Intent intent = new Intent(this,prijava.class);
        startActivity(intent);
    }
    public void RegistracijaKorisnika(){
        registracija.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if(myDb.insertData(username.getText().toString(), password.getText().toString(),adresa.getText().toString()))
                           Toast.makeText(MainActivity.this,"Uspesna registracija",Toast.LENGTH_LONG).show();
                       else
                           Toast.makeText(MainActivity.this,"Neuspesna registracija",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
