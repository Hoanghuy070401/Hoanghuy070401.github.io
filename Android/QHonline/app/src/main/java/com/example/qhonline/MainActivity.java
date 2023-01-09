package com.example.qhonline;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button click;
    EditText edit;
    ImageView img;
    RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        run();
    }
    private void run(){
        click =(Button) findViewById(R.id.btnClick);
        edit = (EditText) findViewById(R.id.editText);
        img = (ImageView) findViewById(R.id.img);
        img.setImageResource(R.drawable.noimage);
        background = (RelativeLayout) findViewById(R.id.layout);
        background.setBackgroundResource(R.drawable.anhnen);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this, "vui long nhap link", Toast.LENGTH_SHORT).show();
                }
                else {
                   new Load().execute(edit.getText().toString());
                }
            }
        });


    }
    private class Load extends AsyncTask<String,Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmm = null;
            try {
                URL u = new URL(strings[0]);
                bmm = BitmapFactory.decodeStream(u.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }
    }


}