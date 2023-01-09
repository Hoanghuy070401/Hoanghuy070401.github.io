package com.example.myactive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText name,passworld;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendinfor();
    }
    private void sendinfor(){
        name = (EditText) findViewById(R.id.editAccount);
        passworld= (EditText) findViewById(R.id.editPass);
        send = (Button) findViewById(R.id.btnsend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActive();

            }
        });
    }
    private void nextActive(){
        String Strmail= name.getText().toString().trim();
        Apuntils.email = Strmail;
        String strPas= passworld.getText().toString().trim();
        Apuntils.pass =strPas;


        Intent intent = new Intent(MainActivity.this,active2.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        name.setText(Apuntils.email);
        passworld.setText(Apuntils.pass);
    }
}