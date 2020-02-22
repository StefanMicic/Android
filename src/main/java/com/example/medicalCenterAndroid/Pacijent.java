package com.example.milicavorkapic;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Pacijent extends AppCompatActivity {
    BazaPregleda myDb;
    Button zakazi,otkazi;
    EditText naslov,pretraga ;
    TableView<String[]> tableView;
    TableView<String[]> tableView1;
    static String[] Headers = {"Naslov","Datum","Vreme","Doktor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacijent);
        tableView= findViewById(R.id.tableView);
        tableView1= findViewById(R.id.tableView1);
        pretraga = findViewById(R.id.pretraga);
        myDb = new BazaPregleda(this);
        zakazi=(Button) findViewById(R.id.zakazi);
        otkazi = (Button) findViewById(R.id.otkazi);
        naslov = (EditText) findViewById(R.id.naslov);
        pretraga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Pretraga();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tableView.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                naslov.setText(clickedData[0]);
            }
        });
        tableView1.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                naslov.setText(clickedData[0]);
            }
        });
        NapuniTabelu(tableView);
        NapuniTabelu1(tableView1);
        zakazi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        zakazi();
                    }
                }
        );
        otkazi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        otkazi();
                    }
                }
        );
    }
    public void Pretraga(){
        String[][] Values=new String[100][4];
        Cursor res= myDb.getAllData();
        int row=0;
        while(res.moveToNext()){
            Toast.makeText(Pacijent.this,"Velicina"+res.getString(4),Toast.LENGTH_LONG).show();
            if(!res.getString(3).isEmpty() || !pretraga.getText().toString().equals(res.getString(4)))
                continue;
            Values[row][0]=res.getString(0);
            Values[row][1]=res.getString(1);
            Values[row][2]=res.getString(2);
            Values[row][3]=res.getString(4);

            row++;
        }
        tableView1.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView1.setHeaderAdapter(new SimpleTableHeaderAdapter(Pacijent.this,Headers));
        tableView1.setColumnCount(4);
        tableView1.setDataAdapter(new SimpleTableDataAdapter(Pacijent.this,Values));
    }
    public void otkazi(){
        Cursor res= myDb.getAllData();
        int row=0;
        String date="",time="";
        while(res.moveToNext()){
            if(!res.getString(0).equals(naslov.getText().toString()))
                continue;
            date=res.getString(1);
            time=res.getString(2);
            break;
        }
        myDb.updateProduct(naslov.getText().toString(),date,time,"d","");
        NapuniTabelu(tableView);
        NapuniTabelu1(tableView1);
    }
    public void zakazi(){
        Cursor res= myDb.getAllData();
        int row=0;
        String date="",time="";
        while(res.moveToNext()){
            if(!res.getString(0).equals(naslov.getText().toString()))
                continue;
            date=res.getString(1);
            time=res.getString(2);
            break;
        }
        myDb.updateProduct(naslov.getText().toString(),date,time,"d",Login.loggedUser);
        NapuniTabelu(tableView);
        NapuniTabelu1(tableView1);
    }
    public void NapuniTabelu(TableView<String[]> tableView){
        String[][] Values=new String[100][4];
        Cursor res= myDb.getAllData();
        int row=0;
        while(res.moveToNext()){
            if(!res.getString(3).equals(Login.loggedUser))
                continue;
            Values[row][0]=res.getString(0);
            Values[row][1]=res.getString(1);
            Values[row][2]=res.getString(2);
            Values[row][3]=res.getString(4);
            row++;
        }
        tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(Pacijent.this,Headers));
        tableView.setColumnCount(4);
        tableView.setDataAdapter(new SimpleTableDataAdapter(Pacijent.this,Values));
    }
    public void NapuniTabelu1(TableView<String[]> tableView){
        String[][] Values=new String[100][4];
        Cursor res= myDb.getAllData();
        int row=0;
        while(res.moveToNext()){
            if(!res.getString(3).isEmpty())
              continue;
            Values[row][0]=res.getString(0);
            Values[row][1]=res.getString(1);
            Values[row][2]=res.getString(2);
            Values[row][3]=res.getString(4);
            row++;
        }
        tableView1.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView1.setHeaderAdapter(new SimpleTableHeaderAdapter(Pacijent.this,Headers));
        tableView1.setColumnCount(4);
        tableView1.setDataAdapter(new SimpleTableDataAdapter(Pacijent.this,Values));
    }
}
