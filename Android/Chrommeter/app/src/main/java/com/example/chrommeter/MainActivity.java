package com.example.chrommeter;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    Button start, pause, stop;
    Chronometer chrom;
    Boolean flag=false;
    ConstraintLayout layout;
    Timer timer;
    long  timeWhenPause=0;
    int i =0;
    ImageView anh ;
    int []arrayAnh={
        R.drawable.a1,R.drawable.a2,
                R.drawable.a3,R.drawable.a4,
                R.drawable.a5,R.drawable.a6,
                R.drawable.a7,R.drawable.a8,
                R.drawable.a9,R.drawable.a10
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }
    public void run(){
        start =(Button) findViewById(R.id.btnstart);
        pause=(Button) findViewById(R.id.btnpause);
        stop = (Button) findViewById(R.id.btnStop);
        chrom =(Chronometer) findViewById(R.id.chrom);
        anh = (ImageView)findViewById(R.id.imganh);

       timer = new Timer();
       Task task= new Task(anh,arrayAnh);
       //công việc , thời gian bắt đầu công việc , thời gian gọi lại công việc
        timer.scheduleAtFixedRate(task,200,400);





        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);



    }
    public class Task extends TimerTask {

        ImageView anh ;
        int []arrayAnh;

        //contructor se tu dong dc goi dau tien khi  chung ta goi class
        public Task(ImageView anh, int[] arrayAnh) {
            this.anh = anh;
            this.arrayAnh = arrayAnh;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    anh.setImageResource(arrayAnh[i]);

                    i=i+1;
                    if (i==10){
                        i=0;
                    }
                }
            });

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnstart:
                if(!flag)//flag=false
                {
                    long currentTime = SystemClock.elapsedRealtime()+timeWhenPause;
                    chrom.setBase(currentTime);
                    chrom.start();
                    flag=true;
                }
                break;
            case R.id.btnpause:
                if (flag){
                    timeWhenPause =chrom.getBase()-SystemClock.elapsedRealtime();
                    chrom.stop();
                    flag=false;
                }
                break;
            case R.id.btnStop:
                if (flag){
                    chrom.stop();
                    timeWhenPause=0;
                    flag=false;
                }
                break;
        }

    }
}