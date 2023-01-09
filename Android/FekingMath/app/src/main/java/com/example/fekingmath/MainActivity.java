package com.example.fekingmath;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    SeekBar time;
    TextView so1 ,so2,so3,point;
    ImageButton check, uncheck;
    int diem;
    int timeGame;
    Timer timer;
    Boolean flag=false;
    ConstraintLayout layout ;
    ArrayList<Integer> image = new ArrayList<>();
    Integer [] color = {
            Color.parseColor("#FFFF33"),
            Color.parseColor("#CCFF99"),
            Color.parseColor("#CCFFCC"),
            Color.parseColor("#00FF99"),
            Color.parseColor("#CC9933")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }
    public void run(){
        time= (SeekBar) findViewById(R.id.sk1);
        so1 = (TextView) findViewById(R.id.txtso1);
        so2 = (TextView) findViewById(R.id.txtso2);
        so3= (TextView) findViewById(R.id.txtkq);
        layout = (ConstraintLayout)findViewById(R.id.layout);
        point = (TextView)findViewById(R.id.txts1);
        check = (ImageButton) findViewById(R.id.imgBtnCheck);
        uncheck = (ImageButton) findViewById(R.id.imgBtnUncheck);
        pheptoan();
        diem =0;
        setcolor();
        timeGame = 100;
        time.setMax(timeGame);
        time.setProgress(timeGame);
        point.setText(String.valueOf(diem));
        if(timeGame == 0){
            fail();
        }

        check.setOnClickListener(view -> dung());

        uncheck.setOnClickListener(view -> sai());
    }
    public int getRamdomMinMax(int min , int max){
        return (int)Math.floor(Math.random()*(max-min))+min;
    }

    public void pheptoan(){
        int r1 = getRamdomMinMax(0,21);
        so1.setText(String.valueOf(r1));
        int r2 = getRamdomMinMax(0,21);
        so2.setText(String.valueOf(r2));
        int r3 = r1+r2;
        int kq= getRamdomMinMax(r3-2,r3+2);
        so3.setText(String.valueOf(kq));
    }
    public void dung(){
        int s1 = Integer.parseInt(so1.getText().toString());
        int s2 = Integer.parseInt(so2.getText().toString());
        int s3 = Integer.parseInt(so3.getText().toString());
         if (s1+s2==s3){
             diem=diem+1;
             point.setText(String.valueOf(diem));
             pheptoan();
             timeGame=100;
            Thoigian();


         }
         else {
             fail();
         }

    }
    public void sai(){
        int s1 = Integer.parseInt(so1.getText().toString());
        int s2 = Integer.parseInt(so2.getText().toString());
        int s3 = Integer.parseInt(so3.getText().toString());
        if (s1+s2!=s3){
            diem=diem+1;
            point.setText(String.valueOf(diem));
            pheptoan();
            timeGame=100;
            Thoigian();

        }
        else {
            fail();
        }

    }
    public void fail(){
       timer.cancel();

        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Một Phút Đạo Lý")
                .setMessage("làm người phải có trí phấn đấu , vấp ngã có tí đã muốn bỏ cuộc rồi à! Tiếp Tục Nào ")
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> run())
                .setNegativeButton(android.R.string.no, (dialogInterface, i) -> Toast.makeText(MainActivity.this, "Đồ Yếu Kém, Ra Xã Hội Thất Bại Thôi Chứ Làm Được Gì ", Toast.LENGTH_SHORT).show());
        build.create().show();//show dialog


    }
    public void setcolor(){
        Collections.addAll(image,color);
        int mauNen =getRamdomMinMax(0,image.size());
        layout.setBackgroundColor(image.get(mauNen));
    }

    public void Thoigian(){
      if (!flag){
           timer = new Timer();
           RunRandom congViec = new RunRandom();
           timer.scheduleAtFixedRate(congViec,1000,100);
            flag =true;
       }
    }
    class RunRandom extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(() -> {
                timeGame= timeGame-1;
                time.setProgress(timeGame);
            });
        }
    }

}