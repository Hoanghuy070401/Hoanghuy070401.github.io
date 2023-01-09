package com.example.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.Utils.Utils;
import com.example.quanlybanhang.model.User;
import com.example.quanlybanhang.retrofit.ApiBanHang;
import com.example.quanlybanhang.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTongtien ,txtSdt,txtEmail,txtUsername;
    EditText editAddress;
    AppCompatButton btnDatHang;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    ApiBanHang apiBanHang;
    int totalItem;
    long tongtien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        initView();
        countItem();
        initControl();
    }

    private void countItem() {
         totalItem = 0;
        for (int i =0 ; i<Utils.mangmuahang.size();i++){
            totalItem = totalItem+Utils.mangmuahang.get(i).getSoluong();
        }
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### "+"$");
         tongtien = getIntent().getLongExtra("tongtien",0);
        txtTongtien.setText(decimalFormat.format(tongtien));
        txtEmail.setText(Utils.user_current.getEmail());
        txtSdt.setText(Utils.user_current.getMobile());
        txtUsername.setText(Utils.user_current.getUsername());

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = editAddress.getText().toString();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(ThanhToanActivity.this, "Vui Lòng Điền Địa Chỉ Nhận Hàng ", Toast.LENGTH_SHORT).show();
                }else {
                    //post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    String str_username =Utils.user_current.getUsername();
                    compositeDisposable.add(apiBanHang.createDonHang(str_email,str_sdt,id,str_username,totalItem, String.valueOf(tongtien),str_diachi,new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        Utils.mangmuahang.clear();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },throwable -> {
                                        //Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                      finish();
                                    }
                            ));
                }
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = (Toolbar) findViewById(R.id.toolbar_thanhtoan);
        txtTongtien=(TextView) findViewById(R.id.txttongtien_thanhtoan);
        txtSdt=(TextView) findViewById(R.id.txtmobile_thanhtoan);
        txtEmail=(TextView) findViewById(R.id.txt_email_thanhtoan);
        txtUsername = (TextView) findViewById(R.id.txtUsername_thanhtoan);
        btnDatHang =  findViewById(R.id.btnThanhtoan);
        editAddress = (EditText) findViewById(R.id.edit_adress_thanhtoan);


    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}