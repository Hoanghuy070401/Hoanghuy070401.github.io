package com.example.myactive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class active2 extends AppCompatActivity {
    EditText name,passworld;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active2);
        name = (EditText) findViewById(R.id.editAccount);
        passworld= (EditText) findViewById(R.id.editPass);
        update = (Button) findViewById(R.id.btnupdate);
        name.setText(Apuntils.email);
        passworld.setText(Apuntils.pass);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });
    }
    private void backActivity(){
        String strEmailUpdate = name.getText().toString().trim();
        String strPassUpdate = passworld.getText().toString().trim();
        Apuntils.email=strEmailUpdate;
        Apuntils.pass=strPassUpdate;
        finish();

    }
}