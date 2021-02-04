package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
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

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Proizvodi extends AppCompatActivity {

    static String[] Headers = {"Naziv","Kolicina"};
    TableView<String[]> tableView;
    BazaProizvoda proizvodi;
    Button brisi;
    EditText naziv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proizvodi);
        tableView= findViewById(R.id.tableView);
        brisi = findViewById(R.id.brisi);
        naziv=findViewById(R.id.search);
        NapuniTabelu(tableView);
        brisiBazu();
        naziv.addTextChangedListener(new TextWatcher() {
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
                showMess("Naziv:"+clickedData[0]);
            }
        });

    }
    public void Pretraga(){
        if(naziv.getText().toString().isEmpty())
            NapuniTabelu(tableView);
        String[][] Values=new String[1000][2];
        proizvodi = new BazaProizvoda(this);
        Cursor res= proizvodi.getAllData();
        int row=0;
        while(res.moveToNext()){
            if(!res.getString(1).contains(naziv.getText().toString()))
                continue;
            Values[row][0]=res.getString(1);
            Values[row][1]=res.getString(2);
            row++;
        }
        tableView.setColumnWeight(0,250);
        tableView.setColumnWeight(1,100);
        tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,Headers));
        tableView.setColumnCount(2);
        tableView.setDataAdapter(new SimpleTableDataAdapter(Proizvodi.this,Values));
    }
    public void showMess(String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(mess);
        builder.show();
    }
    public void NapuniTabelu(TableView<String[]> tableView){
        String[][] Values=new String[1000][2];
        proizvodi = new BazaProizvoda(this);
        Cursor res= proizvodi.getAllData();
        int row=0;
        while(res.moveToNext()){
            Values[row][0]=res.getString(1);
            Values[row][1]=res.getString(2);
            row++;
        }
        tableView.setColumnWeight(0,250);
        tableView.setColumnWeight(1,100);
        tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,Headers));
        tableView.setColumnCount(2);
        tableView.setDataAdapter(new SimpleTableDataAdapter(Proizvodi.this,Values));
    }

    public void brisiBazu(){
        brisi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res1 = proizvodi.getAllData();
                        while(res1.moveToNext()){
                            proizvodi.deleteData(res1.getString(1));
                        }
                        NapuniTabelu(tableView);
                    }
                }
        );
    }

}
