package com.example.alertdialogandcustom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button dialog,customName,customLayout, clickCount;
    ConstraintLayout background;
    View customView;
    TextView textCount;
    int dem=0  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
        countdown();
    }
    public  void run(){
        background = (ConstraintLayout) findViewById(R.id.layout);
        dialog = (Button) findViewById(R.id.btndialog);
        customName= (Button) findViewById(R.id.btncustom);
        customLayout= (Button) findViewById(R.id.btnlayout);

        background.setBackgroundResource(R.drawable.a3);
        dialog.setOnClickListener(this);
        customName.setOnClickListener(this);
        customLayout.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btncustom:
                custom();
                break;
            case R.id.btndialog:
                dialogs();
                break;
            case R.id.btnlayout:
                layoutDialog();
                break;
        }

    }
    public void custom(){
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this)
                .setTitle("thong bao")
                .setMessage("ban co muon xoa ko")
                .setPositiveButton(R.string.co, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "da xoa", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.khong, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "chua xoa ", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false);
        build.create().show();

    }
    public void dialogs(){
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this)
                .setTitle("thong bao")
                .setMessage("ban co muon xoa ko")
                .setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "da xoa", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "chua xoa ", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false);
        build.create().show();

    }
    public void layoutDialog(){
        //b1 tao view
        //b2 lay duong dan
        //B3 gan vao dialog
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
         customView = inflater.inflate(R.layout.activity_customlayout,null);
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this)
                .setTitle("thong bao")
//                .setMessage("ban co muon xoa ko")
                .setView(customView)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       EditText edit1 = (EditText) customView.findViewById(R.id.txt1);
                       EditText edit2 = (EditText) customView.findViewById(R.id.txt2);
                       if (edit1.getText().toString().trim().length()<=0 && edit2.getText().toString().trim().length()<=0){
                           Toast.makeText(MainActivity.this, "vui long nhap day du du lieu ", Toast.LENGTH_SHORT).show();
                       }else {
                           String a = edit1.getText().toString().trim();
                           Toast.makeText(MainActivity.this, "Chao Mung "+a+" Den Voi Ki Nguyen Vo Tan", Toast.LENGTH_SHORT).show();
                       }
                    }
                })

                .setCancelable(false);
        build.create().show();

    }

  public void countdown(){
        clickCount= (Button) findViewById(R.id.btncountdown);
        textCount = (TextView) findViewById(R.id.txtCountdown);

        clickCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown();
            }
        });
  }
  public void countDown(){
      CountDownTimer countDownTimer = new CountDownTimer(10000,1000)//thoi gian dau la so phai dem nguoc ,khoang thoi gian giam vd 10s ,giam 1s
      {
          @Override
          //moi 1 giay thi thuc hien cong viec gi do
          // long l : thoi gian con lai
          public void onTick(long l) {
//              dem=dem+1;
//              textCount.setText(String.valueOf(dem));
               textCount.setText("thoi gian con lai :"+l/1000);
               clickCount.setVisibility(View.GONE);//tat nut click
              textCount.setVisibility(View.VISIBLE);
              background.setBackground(null);
          }

          @Override
          //khi hoan thanh xong thi thuc hien cong viec gi do
          public void onFinish() {
              background.setBackgroundResource(R.drawable.a3);
              clickCount.setVisibility(View.VISIBLE);//mo nut click
              textCount.setVisibility(View.GONE);

          }
      }.start();
  }
}