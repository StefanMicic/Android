package com.example.projekat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ListaKorisnika extends AppCompatActivity {
    static String[] Headers = {"ID","Korisnicko ime","Adresa"};
    DatabaseHelper myDb;
    Korpa korpa;
    Button deleteUser;
    EditText UserID;
    TableView<String[]> tableView;
    private SlidrInterface slidr;

    Button ViewUser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_korisnika);
        tableView= findViewById(R.id.tableView);
        NapuniTabelu(tableView);
        deleteUser= findViewById(R.id.deleteButton);
        UserID = findViewById(R.id.UserID);
        DeleteData();
        slidr= Slidr.attach(this);
        slidr.unlock();
        tableView.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                UserID.setText(clickedData[0]);
            }
        });
    }

    public void NapuniTabelu(TableView<String[]> tableView){
        String[][] Values=new String[100][3];
        myDb = new DatabaseHelper(this);
        Cursor res= myDb.getAllData();
        int row=0;
        while(res.moveToNext()){
            if(res.getString(3).equals("da"))
                continue;
            Values[row][0]=res.getString(0);
            Values[row][1]=res.getString(1);
            Values[row][2]=res.getString(4);
            row++;
        }
        tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,Headers));
        tableView.setColumnCount(3);
        tableView.setDataAdapter(new SimpleTableDataAdapter(ListaKorisnika.this,Values));
    }
    public void DeleteData(){
        deleteUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                korpa=new Korpa(ListaKorisnika.this);
                Cursor res = korpa.getAllProduct();
                Cursor res1 = myDb.getAllData();
                String usernameBrisanje="";
                while(res1.moveToNext()){
                    if(res1.getString(0).equals(UserID.getText().toString()))
                        usernameBrisanje=res1.getString(1);


                }
                while(res.moveToNext()){
                    if(res.getString(3).equals(usernameBrisanje)){
                        Toast.makeText(ListaKorisnika.this,"Nije moguce obrisati kupca koji je vec porucio",Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                Integer deletedRow = myDb.deleteData(UserID.getText().toString());
                if(deletedRow<0)
                    Toast.makeText(ListaKorisnika.this,"Pogresan ID",Toast.LENGTH_LONG).show();
                else
                {
                    NapuniTabelu(tableView);
                }
            }
        });
    }
}
