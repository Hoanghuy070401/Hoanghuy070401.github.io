package com.example.baicao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imgA , imgB, imgC ;
    Button Play;
    TextView txtResult;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }
    public void run(){
        imgA = (ImageView) findViewById(R.id.img1);
        imgB = (ImageView) findViewById(R.id.img2);
        imgC = (ImageView) findViewById(R.id.img3);
        Play = (Button) findViewById(R.id.btnplay);
        txtResult = (TextView) findViewById(R.id.txtView);


        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               xuly();
                text=null;
            }
        });
    }
    public void xuly(){
         text="á thua rồi";
         txtResult.setText(text);
    }
}