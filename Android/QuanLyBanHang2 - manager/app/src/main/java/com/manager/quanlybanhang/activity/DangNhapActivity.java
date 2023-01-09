package com.manager.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manager.quanlybanhang.R;
import com.manager.quanlybanhang.Utils.Utils;
import com.manager.quanlybanhang.retrofit.ApiBanHang;
import com.manager.quanlybanhang.retrofit.RetrofitClient;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    EditText email , pass;
    Button btndangnhap;
    TextView txtDangki , forgetpass;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControll();
    }

    private void initControll() {
        txtDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DangKiActivity.class);
                startActivity(intent);
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ForgerMKActivity.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String str_email= email.getText().toString().trim();
             String str_pass = pass.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(DangNhapActivity.this, "Vui Lòng Điền Email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(str_pass)){
                    Toast.makeText(DangNhapActivity.this, "Vui Lòng Điền Mật Khẩu", Toast.LENGTH_SHORT).show();
                }else {
                    //save
                    Paper.book().write("email",str_email);
                    Paper.book().write("pass",str_pass);
                   dangNhap(str_email,str_pass);
                }
            }
        });
    }

    private void initView() {
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = (EditText)findViewById(R.id.editT_email_dangnhap);
        pass = (EditText)findViewById(R.id.editT_pass_dangnhap);
        btndangnhap = (Button)findViewById(R.id.btnDangnhap);
        txtDangki = (TextView) findViewById(R.id.txt_dangki);
        forgetpass = (TextView) findViewById(R.id.txt_quenMK_dangnhap);


        //read data
        if (Paper.book().read("email")!=null && Paper.book().read("pass")!=null){
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
            if (Paper.book().read("isLogin")!= null){
               boolean flag =  Paper.book().read("isLogin") ;
                if (flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //dangNhap(Paper.book().read("email"),Paper.book().read("pass"));
                        }
                    },1000);
                }
            }
        }
    }
    private void dangNhap( String email,String pass) {
        compositeDisposable.add(apiBanHang.dangnhap(email,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()){
                                isLogin= true;
                                Paper.book().write("isLogin",isLogin);
                                Utils.user_current = userModel.getResult().get(0);

                                //luu infor user
                                Paper.book().write("user",userModel.getResult().get(0));

                                System.out.println( Utils.user_current+"hihi");
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        },
                        throwable -> {
                            Toast.makeText(DangNhapActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));

    }

    @Override
    protected void onResume() {
        super.onResume();
      if (Utils.user_current.getEmail()!=null && Utils.user_current.getPassword()!=null){
          email.setText(Utils.user_current.getEmail());
          pass.setText(Utils.user_current.getPassword());
      }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}