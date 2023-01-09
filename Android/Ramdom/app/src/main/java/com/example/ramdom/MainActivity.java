package com.example.ramdom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    Button btnRamDom1,btnRamDom2;
    TextView txt1, txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();

    }
    public void run(){
        btnRamDom1 =(Button) findViewById(R.id.btnRamdom1);
        btnRamDom2= (Button) findViewById(R.id.btnRamdom2);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);

        btnRamDom2.setOnClickListener(this);
        btnRamDom1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRamdom1:
                //0->99
                int r1=getRamdom(100);
                txt1.setText(String.valueOf(r1));
                break;
            case R.id.btnRamdom2:
                //0-21
                int r2=getRamdomMinMax(10,22);
                txt2.setText(String.valueOf(r2));
                break;
        }
    }
    //random tu 0->max-1
    public int getRamdom(int max){
        return (int)(Math.random() * max);
    }
    //random min->max
    public int getRamdomMinMax(int min , int max){
        return (int)Math.floor(Math.random()*(max-min))+min;
    }
}