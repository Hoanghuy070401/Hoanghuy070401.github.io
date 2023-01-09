package com.example.togglebutton;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ToggleButton togl,toglserver;
    Switch sw1;
    TextView txtsw1 , txtTogl;
    RelativeLayout background;
    EditText link;
    ImageView anh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }
    private void run(){
        togl =(ToggleButton) findViewById(R.id.tglbtn);
        sw1= (Switch) findViewById(R.id.sw1);
        txtsw1 = (TextView) findViewById(R.id.txtsw);
        txtTogl = (TextView) findViewById(R.id.txtTglBtn);
        background = (RelativeLayout) findViewById(R.id.layout);
        link = (EditText)findViewById(R.id.edittextserver);
        toglserver = (ToggleButton)findViewById(R.id.togserver);
        anh = (ImageView) findViewById(R.id.imgView);
        swq();
        tog();

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                swq();
            }
        });
        togl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               tog();
            }
        });
        //server
        toglserver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(toglserver.isChecked()){
                if(link.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this, "vui long dien link", Toast.LENGTH_SHORT).show();
                }else {
                    String group = link.getText().toString().trim();
                    String Fb="https://graph.facebook.com/"+group+"/picture/?type=large";
                    new anh().execute(Fb);
                }
              }else {
                    if(link.getText().toString().trim().length()<=0){
                        Toast.makeText(MainActivity.this, "vui long dien link", Toast.LENGTH_SHORT).show();
                    }else {
                        String nick= link.getText().toString().trim();
                        String yah="https://img.msg.yahoo.com/v1/dislayImage/yahoo/"+nick;
                        new anh().execute(yah);
                    }
                }
            }
        });
    }
    private class  anh extends AsyncTask <String , Integer, Bitmap>{


        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmt = null;
            try {
                URL u = new URL(strings[0]);
                bmt = BitmapFactory.decodeStream(u.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmt;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            anh.setImageBitmap(bitmap);
        }
    }

    //ham ngoai
    public void swq(){
        if (sw1.isChecked()==true){
            txtsw1.setText("dung");
            background.setBackgroundResource(R.drawable.backgoundon);

        }
        else {
            txtsw1.setText("sai");
            background.setBackgroundResource(R.drawable.backgoundoff);
        }
    }
    public void tog(){
        String sorry ="anh yeu be";

        if(togl.isChecked()==true ){
            txtTogl.setText("cheo em");
        }else {
            txtTogl.setText(sorry);
        }
    }
}