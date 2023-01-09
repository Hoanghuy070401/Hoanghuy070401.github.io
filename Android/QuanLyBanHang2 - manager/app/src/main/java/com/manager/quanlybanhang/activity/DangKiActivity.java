package com.manager.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manager.quanlybanhang.R;
import com.manager.quanlybanhang.Utils.Utils;
import com.manager.quanlybanhang.retrofit.ApiBanHang;
import com.manager.quanlybanhang.retrofit.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKiActivity extends AppCompatActivity {
     EditText email,pass, refillpass,mobile,address,username,bridthday;
     Button dangki;
     ApiBanHang apiBanHang;
     CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        initView();
        initControl();
    }

    private void initControl() {
      dangki.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              dangKi();
          }
      });
    }
    private void dangKi() {
        String str_email= email.getText().toString().trim();
        String str_password= pass.getText().toString().trim();
        String str_refillpass= refillpass.getText().toString().trim();
        String str_mobile= mobile.getText().toString().trim();
        String str_address= address.getText().toString();
        String str_brithday= bridthday.getText().toString().trim();
        String str_username= username.getText().toString();
        if (TextUtils.isEmpty(str_email)){
            Toast.makeText(this, "Vui Lòng Điền Email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_password)){
            Toast.makeText(this, "Vui Lòng Điền Mật Khẩu", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_refillpass)){
            Toast.makeText(this, "Vui Lòng Điền Lại Mật Khẩu", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_mobile)){
            Toast.makeText(this, "Vui Lòng Điền Số Điện Thoại", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_address)){
            Toast.makeText(this, "Vui Lòng Điền Địa Chỉ", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_brithday)){
            Toast.makeText(this, "Vui Lòng Điền Ngày Sinh", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_username)){
            Toast.makeText(this, "Vui Lòng Điền Họ Và Tên", Toast.LENGTH_SHORT).show();
        }else {
            if (str_password.equals(str_refillpass)){
                //postdata
               compositeDisposable.add(apiBanHang.dangki(str_email,str_password,str_username,str_mobile,str_address,str_brithday)
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(
                               userModel -> {
                                   if (userModel.isSuccess()){
                                      Utils.user_current.setEmail(str_email);
                                      Utils.user_current.setPassword(str_password);
                                       Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                                       startActivity(intent);
                                       finish();
                                   }else {
                                       Toast.makeText(this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                   }

                               },
                               throwable -> {
                                   Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                       ));
            }else {
                Toast.makeText(this, "Mật Khẩu Không Khớp", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = (EditText) findViewById(R.id.editT_email_dangki);
        pass = (EditText) findViewById(R.id.editT_Mk_dangki);
        refillpass=(EditText) findViewById(R.id.editT_Nhaplai_Mk_dangki);
        mobile =(EditText) findViewById(R.id.editT_mobile_dangki);
        address =(EditText) findViewById(R.id.editT_diachi_dangki);
        username =(EditText) findViewById(R.id.editT_username_dangki);
        bridthday =(EditText) findViewById(R.id.editT_brthday_dangki);
        dangki = (Button) findViewById(R.id.btnDangKi);

    }

    @Override
    protected void onDestroy() {
       compositeDisposable.clear();
        super.onDestroy();
    }
}