package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button cong, tru, nhan , chia;
    EditText so1, so2;
    TextView ketqua;
    ImageView hinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }
    private void run(){
        cong = (Button) findViewById(R.id.btncong);
        tru = (Button) findViewById(R.id.btntru);
        nhan = (Button) findViewById(R.id.btnnhan);
        chia = (Button) findViewById(R.id.btnchia);
        so1 = (EditText) findViewById(R.id.editso1);
        so2 = (EditText) findViewById(R.id.editso2);
        ketqua = (TextView) findViewById(R.id.txtketqua);
        hinh= (ImageView) findViewById(R.id.imgView);

        cong.setOnClickListener(this);
        tru.setOnClickListener(this);
        chia.setOnClickListener(this);
        nhan.setOnClickListener(this);

        hinh.setImageResource(R.drawable.anhhai);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btncong:
                //thuc hien cong viec
                //xet truong hop
                if(so1.getText().toString().trim().length()<=0||so2.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this, "vui long khong de trong", Toast.LENGTH_SHORT).show();
                }else
                {
                    int s1 = Integer.parseInt(so1.getText().toString());
                    int s2 = Integer.parseInt(so2.getText().toString());
                    int tong = s1+s2;
                    ketqua.setText(String.valueOf(tong));
                }
                break;
            case R.id.btntru:
                if(so1.getText().toString().trim().length()<=0||so2.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this, "vui long khong de trong", Toast.LENGTH_SHORT).show();
                }else
                {
                    int s1 = Integer.parseInt(so1.getText().toString());
                    int s2 = Integer.parseInt(so2.getText().toString());
                    int tong = s1-s2;
                    ketqua.setText(String.valueOf(tong));
                }

                break;
            case R.id.btnnhan:
                if(so1.getText().toString().trim().length()<=0||so2.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this, "vui long khong de trong", Toast.LENGTH_SHORT).show();
                }else
                {
                    int s1 = Integer.parseInt(so1.getText().toString());
                    int s2 = Integer.parseInt(so2.getText().toString());
                    int tong = s1*s2;
                    ketqua.setText(String.valueOf(tong));
                }

                break;
            case R.id.btnchia:
                if(so1.getText().toString().trim().length()<=0||so2.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this, "vui long khong de trong", Toast.LENGTH_SHORT).show();
                }else
                {
                    int s1 = Integer.parseInt(so1.getText().toString());
                    int s2 = Integer.parseInt(so2.getText().toString());
                    if(s2==0){
                        Toast.makeText(MainActivity.this, "so ko chia dc cho 0", Toast.LENGTH_SHORT).show();
                    }else {
                        float chia = (float) s1 / s2;
                        ketqua.setText(String.valueOf(chia));
                    }
                }

                break;

        }
    }


}