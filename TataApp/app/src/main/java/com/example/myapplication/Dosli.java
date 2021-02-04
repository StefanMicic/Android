package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Dosli extends AppCompatActivity {
    static String[] Headers = {"Naziv","Kolicina","Datum"};
    TableView<String[]> tableView;
    DosliProizvodi dosliDB;
    Button brisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosli);
        tableView= findViewById(R.id.tableView);
        brisi = findViewById(R.id.brisi);
        brisiBazu();
        NapuniTabelu(tableView);
        tableView.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                showMess("Naziv:"+clickedData[0]);
            }
        });
    }
    public void showMess(String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(mess);
        builder.show();
    }
    public void brisiBazu(){
        brisi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res1 = dosliDB.getAllData();
                        while(res1.moveToNext()){
                            dosliDB.deleteData(res1.getString(1));
                        }
                        NapuniTabelu(tableView);
                    }
                }
        );
    }
    public void NapuniTabelu(TableView<String[]> tableView){
        String[][] Values=new String[1000][3];
        dosliDB = new DosliProizvodi(this);
        Cursor res= dosliDB.getAllData();
        int row=0;
        while(res.moveToNext()){
            Values[row][0]=res.getString(1);
            Values[row][1]=res.getString(2);
            Values[row][2]=res.getString(3);
            row++;
        }

        tableView.setColumnWeight(0,170);
        tableView.setColumnWeight(1,70);
        tableView.setColumnWeight(2,100);
        tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,Headers));
        tableView.setColumnCount(3);
        tableView.setDataAdapter(new SimpleTableDataAdapter(Dosli.this,Values));
    }
}
