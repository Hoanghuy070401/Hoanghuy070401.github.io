package com.example.boctham;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button click;
    TextView TGift;
    int stop;
    int count=0;
    int iteamOnarray;// thay doi thanh bien toan cuc de duoc su dung ben ngoai cac ham
    String[] list={
           "Nguyen Hoang Nam",
           "Nguyen Van Anh",
            "Nguyen Thi Toi",
            "Nguyen Thi Tam",
            "Tran Van Tam",
            "Nguyen van Hai"
    };
    ArrayList<String> List;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }
    public void run(){
        click= (Button) findViewById(R.id.btnRandom);
        TGift= (TextView) findViewById(R.id.txtGif);

        //tao arraylist rong
        List = new ArrayList<>();
        //đổ danh sach vao arraylist
//        for (int i=0;i<list.length;i++){
//            List.add(list[i]);
//        }
        Collections.addAll(List,list);
        
//        List.add(getedit) them tu edittext


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuly();

            }
        });
    }
    public void xuly(){
        timer = new Timer();
        runRandom runrandom = new runRandom();
        timer.scheduleAtFixedRate(runrandom,0,100);

        if (count !=0){
            if (!List.isEmpty()) {
                List.remove(iteamOnarray);
            }
        }
        count=count+1;


    }
    public class runRandom extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   if (!List.isEmpty()){
                       stop=stop+1;
                       if (stop >=20) // thoi gian quay dai ngan tuy vao so lon hay be
                       {
                           Toast.makeText(MainActivity.this, "chuc mung", Toast.LENGTH_SHORT).show();
                           timer.cancel();//dung danh sach
                           stop =0;
                       }else {
                           int total = List.size();//tong phan tu trong Arraylist
                           iteamOnarray= getRamdom(total);//
                           TGift.setText(List.get(iteamOnarray));

                       }
                   }else {
                       TGift.setText("da het");
                   }
                }
            });

        }
    }
    public int getRamdom(int max){
        return (int)(Math.random() * max);
    }

}