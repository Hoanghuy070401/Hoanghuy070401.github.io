package com.example.qlnv1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME = "Manager.sqlite";
    SQLiteDatabase database;


    ListView listView;
    //luu tru tat ca doi tuong nhan vien
    ArrayList<NhanVien> list;
    AdapterNhanVien adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien ",null);
        cursor.moveToFirst();
        Toast.makeText(this, cursor.getString(1), Toast.LENGTH_SHORT).show();

       //goi ham
        addControls();
        readData();

    }
    //khoi tao ham khai bao cac doi tuong len listview
    public void addControls(){
        listView =  findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterNhanVien(this,list);
        listView.setAdapter(adapter);
    }
    public void readData(){
        database = Database.initDatabase(.this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien ",null);
        list.clear();
      for (int i=0;i<cursor.getCount();i++){
          cursor.moveToPosition(i);
          int id= cursor.getInt(0);
          String Name= cursor.getString(1);
          int SDT = cursor.getInt(2);
          String DiaChi= cursor.getString(3);
          byte[] picture= cursor.getBlob(4);
          list.add(new NhanVien(id,Name,SDT,DiaChi,picture));

      }
      //set lai giao dien
      adapter.notifyDataSetChanged();
    }
}