package com.example.milicavorkapic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Doktor extends AppCompatActivity {
    BazaPregleda myDb;
    Button add,profile ;
    EditText naslov , datum,vreme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doktor);
        myDb = new BazaPregleda(this);
        add = (Button) findViewById(R.id.dodaj);
        profile = (Button) findViewById(R.id.profile);
        naslov = (EditText) findViewById(R.id.naslov);
        datum= (EditText) findViewById(R.id.date);
        vreme = (EditText) findViewById(R.id.time);

        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addAppointment();
                    }
                }
        );
        profile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDoctorProfile();
                    }
                }
        );
    }
    public void openDoctorProfile(){
        Intent intent = new Intent(this,ProfileDoctor.class);
        startActivity(intent);
    }
    public void addAppointment(){
        if(datum.getText().toString().trim().isEmpty()||vreme.getText().toString().trim().isEmpty()||
        naslov.getText().toString().trim().isEmpty()){
            Toast.makeText(Doktor.this,"Morate uneti sva polja",Toast.LENGTH_LONG).show();
            return;
        }
        if (Integer.parseInt(datum.getText().toString())<1&&Integer.parseInt(datum.getText().toString())>30)
            return;
        if (Integer.parseInt(vreme.getText().toString())<1&&Integer.parseInt(vreme.getText().toString())>24)
            return;
       myDb.insertData(naslov.getText().toString(),datum.getText().toString(),
                Integer.parseInt(vreme.getText().toString()),"",Login.loggedUser);
       Toast.makeText(Doktor.this,"Uspesno dodat termin",Toast.LENGTH_LONG).show();
    }
}
