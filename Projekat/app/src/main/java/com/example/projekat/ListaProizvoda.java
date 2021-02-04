package com.example.projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ListaProizvoda extends AppCompatActivity {
    static String[] Headers = {"Name","Price","Quantity"};
    dataProduct myDb;
    Korpa korpa;
    Button deleteProduct;
    Button addProduct;
    Button search;
    Button btnUpdate;
    EditText name ,price,quantity;
    Button users;
    Button log;

    TableView<String[]> tableView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proizvoda);
        tableView= findViewById(R.id.tableProizvodi);
        name = findViewById(R.id.name);
        users = findViewById(R.id.Users);
        korpa=new Korpa(this);
        price=findViewById(R.id.price);
        log=findViewById(R.id.logout);
        quantity=findViewById(R.id.quantity);
        search=findViewById(R.id.pretraga);
        addProduct=findViewById(R.id.addP);
        deleteProduct=findViewById(R.id.delete);
        btnUpdate=findViewById(R.id.updateButton);
        NapuniTabelu(tableView);

        add();
        DeleteP();
        UpdateP();
        Search();
        log.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        odjava();
                    }
                }
        );
        users.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OpenUsers();
                    }
                }
        );
    }
    public void OpenUsers(){
        Intent intent = new Intent(this,ListaKorisnika.class);
        startActivity(intent);
    }
    public void NapuniTabelu(TableView<String[]> tableView){
        String[][] Values=new String[100][3];
        myDb = new dataProduct(this);
        Cursor res= myDb.getAllProduct();
        int row=0;
        while(res.moveToNext()){
            Values[row][0]=res.getString(0);
            Values[row][1]=res.getString(1);
            Values[row][2]=res.getString(2);
            row++;
        }
        tableView.setHeaderBackgroundColor(Color.parseColor("gray"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(ListaProizvoda.this,Headers));
        tableView.setColumnCount(3);
        tableView.setDataAdapter(new SimpleTableDataAdapter(ListaProizvoda.this,Values));
    }
    public void Search(){
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[][] Values=new String[100][3];
                myDb = new dataProduct(ListaProizvoda.this);
                Cursor res= myDb.getAllProduct();
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
                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(ListaProizvoda.this,Headers));
                tableView.setColumnCount(3);
                tableView.setDataAdapter(new SimpleTableDataAdapter(ListaProizvoda.this,Values));
            }
        });
    }
    public void DeleteP(){
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                korpa.deleteProduct(name.getText().toString());
                Integer deletedRow = myDb.deleteData(name.getText().toString());
                if(deletedRow<0)
                    Toast.makeText(ListaProizvoda.this,"Pogresan ID",Toast.LENGTH_LONG).show();
                else
                {
                    NapuniTabelu(tableView);
                }
            }
        });
    }
    public void UpdateP(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Integer.parseInt(price.getText().toString());
                    Integer.parseInt(quantity.getText().toString());
                }
                catch (Exception exeption){
                    Toast.makeText(ListaProizvoda.this,"Morate uneti brojeve za cenu i kolicinu",Toast.LENGTH_LONG).show();
                    return;
                }
                if(name.getText().toString().isEmpty()||price.getText().toString().isEmpty()||quantity.getText().toString().isEmpty())
                    return;
                if(myDb.updateProduct(name.getText().toString(),price.getText().toString(),quantity.getText().toString()))
                    NapuniTabelu(tableView);
                else
                    Toast.makeText(ListaProizvoda.this,"Neuspesno azuriranje",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void odjava(){
        MainActivity.prijavljeni="";
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void add(){
        addProduct.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Integer.parseInt(price.getText().toString());
                            Integer.parseInt(quantity.getText().toString());
                        }
                        catch (Exception exeption){
                            Toast.makeText(ListaProizvoda.this,"Morate uneti brojeve za cenu i kolicinu",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(name.getText().toString().isEmpty()||price.getText().toString().isEmpty()||quantity.getText().toString().isEmpty())
                            return;
                        if (myDb.insertProduct(name.getText().toString(), price.getText().toString(), quantity.getText().toString())) {
                            NapuniTabelu(tableView);
                        } else
                            Toast.makeText(ListaProizvoda.this, "Neuspesno dodavanje", Toast.LENGTH_LONG).show();
                    }

        });
    }


}
