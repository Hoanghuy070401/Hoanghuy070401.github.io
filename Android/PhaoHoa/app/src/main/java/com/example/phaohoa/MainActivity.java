package com.example.phaohoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button btnClick;
    EditText edit;
    Chronometer time;
    TextView so;
    int timeCountDown;
    Timer timer;
    ConstraintLayout layout;


    int stt = 0;
    int[] mangAnh = {
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6,
            R.drawable.a7,
            R.drawable.a8,
            R.drawable.a9,
            R.drawable.a10,
            R.drawable.a11
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();

    }

    public void run() {
        btnClick = (Button) findViewById(R.id.btnCount);
        edit = (EditText) findViewById(R.id.editText);
        time = (Chronometer) findViewById(R.id.chnTime);
        so = (TextView) findViewById(R.id.txtShow);
        layout = (ConstraintLayout) findViewById(R.id.layout);

        //time.setVisibility(View.INVISIBLE);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                xuly();
            }
        });
    }

    public void xuly() {
        if (edit.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "vui long nhap time", Toast.LENGTH_SHORT).show();
        }
        else
        {
            timeCountDown = Integer.parseInt(edit.getText().toString().trim());
            if (timeCountDown > 0) {
                time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        if (timeCountDown > 0) {
                            timeCountDown =timeCountDown-1;
                            so.setText(String.valueOf(timeCountDown));
                        } else {
                            fireJob();
                            time.stop();
                        }
                    }
                });
                time.start();
            } else {
                Toast.makeText(this, "Time ko duoc nho hon 0", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void fireJob() {
        timer = new Timer();
        Fire fire = new Fire(layout,mangAnh);
        timer.scheduleAtFixedRate(fire,1000,100);

    }

    public class Fire extends TimerTask {

        ConstraintLayout layout;
        int[] mangAnh;

        public Fire( ConstraintLayout layout,int[] mangAnh) {

            this.layout = layout;
            this.mangAnh = mangAnh;
        }

        @Override
        public void run() {

            layout.setBackgroundResource(mangAnh[stt]);
            stt = stt+1;
            if (stt > 11){
                stt=0;
            }

        }
    }
}