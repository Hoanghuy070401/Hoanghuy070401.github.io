package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnLog, btnToastShort, btnToastLong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public  void init(){
        btnLog =(Button) findViewById(R.id.btnLog);
        btnToastLong = (Button) findViewById(R.id.btnToastLong);
        btnToastShort = (Button) findViewById(R.id.btnTastShort);


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Hello","chao the gioi");
                setContentView(R.layout.activity_main3);
            }
        });
        btnToastLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("chao ","chao thang ngu");
            }
        });
        btnToastShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hi", " ngu vl");
            }
        });

    }
}