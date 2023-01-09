package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ListView food;
    ArrayList<hocsinh> student = new ArrayList<>();


    hocsinh[] Hocsinh = {
            new hocsinh("huy",R.drawable.a1,21),
            new hocsinh("thuy",R.drawable.a2,25),
            new hocsinh("van",R.drawable.a3,22),
            new hocsinh("nhien",R.drawable.a4,26),
            new hocsinh("hoang",R.drawable.a5,29),
            new hocsinh("thao",R.drawable.a6,28),
            new hocsinh("huynh",R.drawable.a7,27),
            new hocsinh("vy",R.drawable.a8,25),
            new hocsinh("tam",R.drawable.a9,23),
            new hocsinh("manh",R.drawable.a10,24)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }
    public void run(){
        food = (ListView) findViewById(R.id.ListViewfood);
        Collections.addAll(student,Hocsinh);

        Custom adapter = new Custom(MainActivity.this,student);
        food.setAdapter(adapter);
    }
}