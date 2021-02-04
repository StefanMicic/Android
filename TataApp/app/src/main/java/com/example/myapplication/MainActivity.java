package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    BazaProizvoda myDb;
    DosliProizvodi dosliDB;
    OtisliProizvodi otisliDB;
    EditText naziv, kolicina, datum;
    Button dosao;
    Button otisao ;
    Button date;
    Button istorijaDoslih;
    Button istorijaOtislih ;
    Button stanje ;
    Button brisi;
    public static String prijavljeni;
    Calendar c;
    DatePickerDialog dpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new BazaProizvoda(this);
        dosliDB = new DosliProizvodi(this);
        date = findViewById(R.id.date);
        otisliDB = new OtisliProizvodi(this);
        naziv= findViewById(R.id.naziv);
        kolicina = (EditText) findViewById(R.id.kol);
        datum = (EditText) findViewById(R.id.datum);
        stanje = (Button) findViewById(R.id.stanje);
        brisi = (Button) findViewById(R.id.brisi);
        dosao = (Button) findViewById(R.id.dosao);
        otisao = (Button) findViewById(R.id.otisao);
        istorijaDoslih = findViewById(R.id.dosliIstorija);
        istorijaOtislih=findViewById(R.id.otisliIstorija);
        dosaoProizvod();
        otisaoProizvod();
        ////
/*
        File file =new File("tata.txt");
        if(!file.exists())
            showMess("A");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                showMess(sc.nextLine());
        }catch (Exception e){
           // showMess("A");
        }*/
        /////
        istorijaDoslih.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IstorijaDoslih();
                    }
                }
        );
        istorijaOtislih.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IstorijaOtislih();
                    }
                }
        );
        stanje.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stanje();
                    }
                }
        );
        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                c=Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                       datum.setText(i2+"."+(i1+1)+".2019.");
                    }
                },day,month,year);
                dpd.show();
            }
        });
    }
    public void IstorijaDoslih(){
        Intent intent = new Intent(this,Dosli.class);
        startActivity(intent);
    }
    public void IstorijaOtislih(){
        Intent intent = new Intent(this,Otisli.class);
        startActivity(intent);
    }
    public void stanje(){
        Intent intent = new Intent(this,Proizvodi.class);
        startActivity(intent);
    }

    public void dosaoProizvod(){
        dosao.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(naziv.getText().toString().trim().isEmpty()||kolicina.getText().toString().trim().isEmpty()||
                                datum.getText().toString().trim().isEmpty()){
                            Toast.makeText(MainActivity.this, "Sva polja moraju biti popunjena!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Cursor res1 = myDb.getAllData();
                        while(res1.moveToNext()){
                            if(res1.getString(1).toString().equals(naziv.getText().toString())){
                                int update =Integer.parseInt(res1.getString(2)) +
                                        Integer.parseInt(kolicina.getText().toString());
                                myDb.updateProduct(res1.getString(1).toString(), update);
                                    dosliDB.insertData(naziv.getText().toString(),
                                            Integer.parseInt(kolicina.getText().toString()),
                                            datum.getText().toString());
                                    Toast.makeText(MainActivity.this, "Uspesno dodavanje", Toast.LENGTH_LONG).show();
                                    datum.setText("");
                                    naziv.setText("");
                                    kolicina.setText("");
                                return;
                            }
                        }
                        if(myDb.insertData(naziv.getText().toString(),Integer.parseInt(kolicina.getText().toString()))) {
                            Toast.makeText(MainActivity.this, "Uspesno dodavanje", Toast.LENGTH_LONG).show();
                            dosliDB.insertData(naziv.getText().toString(),
                                    Integer.parseInt(kolicina.getText().toString()),
                                    datum.getText().toString());
                            datum.setText("");
                            naziv.setText("");
                            kolicina.setText("");
                        }
                        else
                            Toast.makeText(MainActivity.this,"Neuspesno dodavanje",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void otisaoProizvod(){
        otisao.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            Cursor res1 = myDb.getAllData();
                            Boolean provera = false;
                            while(res1.moveToNext()){
                                if(res1.getString(1).toString().equals(naziv.getText().toString())){
                                    if(Integer.parseInt(res1.getString(2))<Integer.parseInt(kolicina.getText().toString())){
                                        Toast.makeText(MainActivity.this,"Nema dovoljno na stanju",Toast.LENGTH_LONG).show();
                                    }else {
                                        myDb.updateProduct(res1.getString(1).toString(), Integer.parseInt(res1.getString(2)) -
                                                Integer.parseInt(kolicina.getText().toString()));
                                        otisliDB.insertData(naziv.getText().toString(),
                                                Integer.parseInt(kolicina.getText().toString()),
                                                datum.getText().toString());
                                        provera=true;
                                        Toast.makeText(MainActivity.this, "Uspesno!", Toast.LENGTH_LONG).show();
                                        datum.setText("");
                                        naziv.setText("");
                                        kolicina.setText("");
                                    }
                                    return;
                                }
                            }
                        Toast.makeText(MainActivity.this, "Nije pronadjen proizvod!", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void showMess(String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(mess);
        builder.show();
    }
}
