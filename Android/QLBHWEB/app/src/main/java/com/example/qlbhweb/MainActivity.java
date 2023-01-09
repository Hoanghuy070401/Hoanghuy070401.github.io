package com.example.qlbhweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton thongbao, hanghoa, hoadon;
    ConstraintLayout home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }

    private void run() {
        thongbao = (ImageButton) findViewById(R.id.tmgbtnthongbao);
        hanghoa =(ImageButton) findViewById(R.id.imghanghoa);
        hoadon =(ImageButton) findViewById(R.id.imgbtnbill);
        home = (ConstraintLayout)findViewById(R.id.layoutHome);

        home.setBackgroundResource(R.drawable.background);

        thongbao.setOnClickListener(this);
        hanghoa.setOnClickListener(this);
        hoadon.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tmgbtnthongbao:
                Toast.makeText(this, "thongbao", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imghanghoa:
                Toast.makeText(this, "hang hoa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgbtnbill:
                Toast.makeText(this, "hoa don", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}