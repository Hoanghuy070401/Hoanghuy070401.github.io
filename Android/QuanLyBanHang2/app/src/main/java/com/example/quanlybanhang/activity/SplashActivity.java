package com.example.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.quanlybanhang.R;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        Thread thread= new Thread(){
            public void run (){
             try {

             }catch (Exception ex){

             }finally {
                     Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                     startActivity(intent);
                     finish();
             }
            }
        };
        thread.start();
    }
}