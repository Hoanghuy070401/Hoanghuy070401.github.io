package com.example.activiphu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txt1, txt2;
    SeekBar sk1,sk2;
    ImageView anh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runs1();
    }
    private void runs1(){
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 =(TextView) findViewById(R.id.txt2);
        sk1= (SeekBar) findViewById(R.id.sk1);
        sk2 =(SeekBar) findViewById(R.id.sk2);
        anh = (ImageView) findViewById(R.id.anh);
        final int min =1;
        int max=10;
        sk1.setMax(max);
        sk1.setProgress(5);

        int i = sk1.getProgress();//để câu lệnh được thực thi lúc mở lên
        loadAnh(i,min);

        int price = sk1.getProgress();
        txt1.setText(String.valueOf(price));
        sk1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txt1.setText(String.valueOf(i));
                loadAnh( i , min);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sk2.setMax(10);
        sk2.setProgress(5);
        int s2 = sk2.getProgress()+10;//khai báo biến có giá trị theo progress changed
        txt2.setText(String.valueOf(s2));//set gia tri ban dau cho text
        sk2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
               progress =progress+10;//giá trị bat dau 10
                txt2.setText(String.valueOf(progress));//set gia tri theo thanh keo cho text
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void loadAnh(int i , int min){
        i =i+min;
        String nameAnh = "a"+i;//set anh theo chuoi tang của progress
        int resID = getResources().getIdentifier(nameAnh,"drawable",getPackageName());//bien Identifier cần truyền chuỗi tên anh, noi lưu trữ , getPackageName
        anh.setImageResource(resID);
    }
}