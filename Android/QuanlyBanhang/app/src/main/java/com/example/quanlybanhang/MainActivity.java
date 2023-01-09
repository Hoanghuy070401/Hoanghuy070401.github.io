package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME="quanlynhanvien.sqlite";
    SQLiteDatabase database;

    ListView listView;
    ArrayList<NhanVien> listNV;
    AdapterNhanVien adapter;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            database = DataBase.initDatabase(MainActivity.this,DATABASE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cursor cursor=database.rawQuery("SELECT * FROM NhanVien",null);
        cursor.moveToFirst();
        addControls();
        readData();

    }
    private void addControls(){
        btnAdd =(Button)findViewById(R.id.btnadd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,addActivity.class);
                startActivity(intent);
            }
        });
        listView= (ListView) findViewById(R.id.listview);
        listNV = new ArrayList<>();
        adapter = new AdapterNhanVien(MainActivity.this,listNV);
        listView.setAdapter(adapter);
    }
    private void readData(){
        try {
            database = DataBase.initDatabase(MainActivity.this,DATABASE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cursor cursor=database.rawQuery("SELECT * FROM NhanVien",null);
       listNV.clear();
        for (int i=0;i<cursor.getCount();i++){
           cursor.moveToPosition(i);
           int Id = cursor.getInt(0);
           String Name = cursor.getString(1);
           String SDT = cursor.getString(2);
           String Tuoi  = cursor.getString(3);
           int CMND = cursor.getInt(4);
           String Diachi = cursor.getString(5);
           byte[] priture = cursor.getBlob(6);
           listNV.add(new NhanVien(Id, Name, SDT, Tuoi,CMND,Diachi,priture));
       }
       adapter.notifyDataSetChanged();
    }
}