package com.manager.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.manager.quanlybanhang.R;
import com.manager.quanlybanhang.Utils.Utils;
import com.manager.quanlybanhang.retrofit.ApiBanHang;
import com.manager.quanlybanhang.retrofit.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgerMKActivity extends AppCompatActivity {
    EditText email ;
    AppCompatButton btnReset;
    ProgressBar progressBar;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forger_mkactivity);
        initView();
        initConTrol();
    }

    private void initConTrol() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(ForgerMKActivity.this, "Email Trống", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(ForgerMKActivity.this, "Mật Khẩu Dễ Vậy Mà Cũng Quên! Não Cá Vàng Thật Chứ !!", Toast.LENGTH_SHORT).show();
                  compositeDisposable.add(apiBanHang.resetpas(str_email)
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(
                                  userModel -> {
                                     if (userModel.isSuccess()){
                                         Toast.makeText(ForgerMKActivity.this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                         Intent intent =new Intent(getApplicationContext(),DangNhapActivity.class);
                                         startActivity(intent);
                                         finish();
                                     }else {
                                         Toast.makeText(ForgerMKActivity.this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                      progressBar.setVisibility(View.INVISIBLE);
                                  },
                                  throwable -> {
                                      Toast.makeText(ForgerMKActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                      progressBar.setVisibility(View.INVISIBLE);
                                  }
                          ));
                }
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = (EditText) findViewById(R.id.forgetpass_editemail);
        progressBar = (ProgressBar)findViewById(R.id.progress_forgetMK);
        btnReset = (AppCompatButton) findViewById(R.id.btn_forgetpass);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}