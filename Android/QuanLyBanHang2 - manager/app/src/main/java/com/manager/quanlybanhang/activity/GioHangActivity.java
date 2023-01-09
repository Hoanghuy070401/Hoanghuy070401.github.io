package com.manager.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.manager.quanlybanhang.R;
import com.manager.quanlybanhang.Utils.Utils;
import com.manager.quanlybanhang.apdapter.GioHangAdapter;
import com.manager.quanlybanhang.model.EvenBus.TinhTongEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    TextView giohangtrong,tongtien ;
    Toolbar toolbar;
    RecyclerView recyclerView;
    AppCompatButton btnMuahang;
    GioHangAdapter adapter;
    long tongtiensp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        intControl();
        totalCart();
    }

    private void totalCart() {
        tongtiensp=0;
        for (int i= 0; i<Utils.mangmuahang.size();i++){
            tongtiensp = tongtiensp + (Utils.mangmuahang.get(i).getGia()*Utils.mangmuahang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### "+"$");
        tongtien.setText(decimalFormat.format(tongtiensp));
    }

    private void intControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Utils.manggiohang.size()==0){
            giohangtrong.setVisibility(View.VISIBLE);

        }else {
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }
        btnMuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tongtiensp==0){
                    Toast.makeText(GioHangActivity.this, "Vui Lòng Chọn Sản Phẩm Cần Mua", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(),ThanhToanActivity.class);
                    intent.putExtra("tongtien",tongtiensp);
                    Utils.manggiohang.clear();
                    startActivity(intent);
                }

            }
        });

    }

    private void initView() {
        giohangtrong = (TextView) findViewById(R.id.txtgiohangtrong);
        toolbar =(Toolbar) findViewById(R.id.toolbar_giohang);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_giohang);
        tongtien = (TextView) findViewById(R.id.txtTongtienGioHang);
        btnMuahang=  findViewById(R.id.btnmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenTinhTien(TinhTongEvent event){
        if (event != null){
            totalCart();
        }
    }
}