package com.example.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlybanhang.R;
import com.example.quanlybanhang.Utils.Utils;
import com.example.quanlybanhang.model.GioHang;
import com.example.quanlybanhang.model.SanPhamMoi;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChitietActivity extends AppCompatActivity {
    TextView ten, gia,mota;
    Button btnGioHang;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;
    int soluong;
    boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        runView();
        ActionToolBar();
        intiData();
        intiControl();
    }

    private void intiControl() {
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themgiohang();
            }
        });
    }

    private void themgiohang() {
        if (Utils.manggiohang.size()>0){
             flag= false;
             soluong= Integer.parseInt(spinner.getSelectedItem().toString());
             for (int i = 0;i< Utils.manggiohang.size(); i++){
                if(Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = sanPhamMoi.getGia()*Utils.manggiohang.get(i).getSoluong();
                   Utils.manggiohang.get(i).setGia(gia);
                   flag=true;
                   System.out.println(i+"hihi");
                }
            }
            if (flag==false){
               long gia = sanPhamMoi.getGia()*soluong;
                GioHang gioHang = new GioHang();
                gioHang.setGia(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTen(sanPhamMoi.getTen());
                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                Utils.manggiohang.add(gioHang);
            }

        }else {
            int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = sanPhamMoi.getGia()*soluong;
            GioHang gioHang = new GioHang();
            gioHang.setGia(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTen(sanPhamMoi.getTen());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }

        if(Utils.manggiohang != null){
            int totalItem = 0;
            for (int i =0 ; i<Utils.manggiohang.size();i++){
                totalItem = totalItem+Utils.manggiohang.get(i).getSoluong();
            }

            badge.setText(String.valueOf(totalItem));
        }


    }

    private void intiData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        ten.setText(sanPhamMoi.getTen());
        mota.setText(sanPhamMoi.getMota());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        gia.setText("Giá "+decimalFormat.format(sanPhamMoi.getGia())+" $");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);

    }

    private void runView() {
        ten =(TextView) findViewById(R.id.chitiet_txtten);
        gia = (TextView) findViewById(R.id.chitiet_txtgia);
        mota =(TextView) findViewById(R.id.txt_motachitiet);
        btnGioHang = (Button) findViewById(R.id.chitiet_btnGiohang);
        spinner = (Spinner) findViewById(R.id.chitiet_Spinner_soluong);
        imghinhanh = (ImageView) findViewById(R.id.imgchitiet_New);
        toolbar = (Toolbar) findViewById(R.id.toolbar_CT);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegioang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang= new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);

            }
        });
        badge = (NotificationBadge) findViewById(R.id.menu_s1);

    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.manggiohang != null){
            int totalItem = 0;
            for (int i =0 ; i<Utils.manggiohang.size();i++){
                totalItem = totalItem+Utils.manggiohang.get(i).getSoluong();
            }

            badge.setText(String.valueOf(totalItem));
        }
    }
}