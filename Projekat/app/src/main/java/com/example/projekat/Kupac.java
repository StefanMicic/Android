package com.example.projekat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Kupac extends AppCompatActivity {
    static String[] Headers = {"Ime","Cena","Kolicina"};
    Korpa myDb;
    dataProduct dataP;
    Button log;
    Button search;
    EditText name;
    EditText racun;
    Button plati;
    Button cancel;
    TableView<String[]> tableView;
    TableView<String[]> tableKorpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kupac);
        tableView= findViewById(R.id.tableProizvodi);
        tableKorpa= findViewById(R.id.tableKorpa);
        cancel = findViewById(R.id.odustani);
        log=findViewById(R.id.logout);
        name = findViewById(R.id.name);
        plati = findViewById(R.id.plati);
        search=findViewById(R.id.pretraga);
        racun = findViewById(R.id.Racun);
        myDb = new Korpa(this);
        dataP = new dataProduct(this);
        NapuniKorpu(tableKorpa);
        NapuniTabelu(tableView);
        Search();
        tableKorpa.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {

            }
        });
        tableView.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                Cursor res= dataP.getAllProduct();
                while(res.moveToNext()){
                    if(res.getString(0).equals(clickedData[0])){
                        int quantity = Integer.parseInt(clickedData[2]);
                        if(quantity==0){
                            Toast.makeText(Kupac.this,"Nema vise ovog proizvoda",Toast.LENGTH_LONG).show();
                            return;
                        }
                        quantity--;
                        dataP.updateProduct(clickedData[0],clickedData[1],String.valueOf(quantity));
                        NapuniTabelu(tableView);
                    }
                }
                Cursor res1= myDb.getAllProduct();
                StringBuffer buffer = new StringBuffer();
                int quantity=1;
                while(res1.moveToNext()){
                    if(res1.getString(0).equals(clickedData[0])){
                        quantity = Integer.parseInt(res1.getString(2));
                        quantity++;
                        myDb.updateProduct(clickedData[0],clickedData[1],String.valueOf(quantity),MainActivity.prijavljeni);
                        NapuniKorpu(tableKorpa);
                        break;
                    }
                }
                myDb.insertProduct(clickedData[0],clickedData[1],"1",MainActivity.prijavljeni);
                NapuniKorpu(tableKorpa);
            }
        });
        log.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odjava();
                    }
                }
        );
        plati.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IsprazniKorpu(tableKorpa);
                    }
                }
        );
        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Odustani();
                    }
                }
        );
    }
    public void odjava(){
        MainActivity.prijavljeni="";
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void Odustani(){
        Cursor res= myDb.getAllProduct();
        HashMap<String,String> NazivKolicina = new HashMap<String, String>();
        while(res.moveToNext()){
                if(MainActivity.prijavljeni.equals(res.getString(3)))
                    NazivKolicina.put(res.getString(0),res.getString(2));
            }
        Cursor res1= dataP.getAllProduct();
        int quantity;
        while(res1.moveToNext()){
            if(NazivKolicina.containsKey(res1.getString(0))){
                quantity = Integer.parseInt(res1.getString(2));
                quantity+=Integer.parseInt(NazivKolicina.get(res1.getString(0)));
                dataP.updateProduct(res1.getString(0),res1.getString(1),String.valueOf(quantity));
                NapuniTabelu(tableView);
            }
        }
        IsprazniKorpu(tableKorpa);
    }
    public void IsprazniKorpu(TableView<String[]> tableView1){
            myDb.deleteValues(MainActivity.prijavljeni);
            NapuniKorpu(tableView1);
    }
    public void NapuniTabelu(TableView<String[]> tableView){
        String[][] Values=new String[100][3];
        Cursor res= dataP.getAllProduct();
        int row=0;
        while(res.moveToNext()){
            Values[row][0]=res.getString(0);
            Values[row][1]=res.getString(1);
            Values[row][2]=res.getString(2);
            row++;
        }
        tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(Kupac.this,Headers));
        tableView.setColumnCount(3);
        tableView.setDataAdapter(new SimpleTableDataAdapter(Kupac.this,Values));
    }
    public void NapuniKorpu(TableView<String[]> tableView){

        Cursor res= myDb.getAllProduct();
        String[][] Values=new String[res.getCount()][3];
        int row=0;
        int zaPlacanje=0;
        while(res.moveToNext()){
            if(res.getString(3).equals(MainActivity.prijavljeni)) {
                Values[row][0] = res.getString(0);
                if (Values[row][0] == null)
                    continue;
                Values[row][1] = res.getString(1);
                Values[row][2] = res.getString(2);

                int quantity = Integer.parseInt(res.getString(2));
                int price = Integer.parseInt(Values[row][1]);
                zaPlacanje += (quantity * price);
                row++;
            }
        }
        tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(Kupac.this,Headers));
        tableView.setColumnCount(3);
        tableView.setDataAdapter(new SimpleTableDataAdapter(Kupac.this,Values));
        racun.setText(String.valueOf(zaPlacanje));
    }
    public void Search(){
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[][] Values=new String[100][3];
                Cursor res= dataP.getAllProduct();
                int row=0;
                if(name.getText().toString().isEmpty()){
                    while(res.moveToNext()){
                        Values[row][0] = res.getString(0);
                        Values[row][1] = res.getString(1);
                        Values[row][2] = res.getString(2);
                        row++;
                    }
                }else{
                    while(res.moveToNext()){
                        if((res.getString(0).contains(name.getText().toString()))){
                            Values[row][0] = res.getString(0);
                            Values[row][1] = res.getString(1);
                            Values[row][2] = res.getString(2);
                            row++;
                        }
                    }
                }
                tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(Kupac.this,Headers));
                tableView.setColumnCount(3);
                tableView.setDataAdapter(new SimpleTableDataAdapter(Kupac.this,Values));
            }
        });
    }

}
