package com.manager.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.manager.quanlybanhang.R;
import com.manager.quanlybanhang.Utils.Utils;
import com.manager.quanlybanhang.apdapter.DonHangApdapter;
import com.manager.quanlybanhang.retrofit.ApiBanHang;
import com.manager.quanlybanhang.retrofit.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView recyclerDonhang;
    Toolbar toolbarXD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don);
        initView();
        initToolBar();
        getOrder();

    }

    private void getOrder() {
        compositeDisposable.add(apiBanHang.xemDonHang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            DonHangApdapter apdapter = new DonHangApdapter(getApplicationContext(),donHangModel.getResult());
                            recyclerDonhang.setAdapter(apdapter);
                        },
                        throwable -> {
                            System.out.println("kkk");

                        }
                ));
    }

    private void initToolBar() {
        setSupportActionBar(toolbarXD);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarXD.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        recyclerDonhang= findViewById(R.id.recycler_DH);
        toolbarXD= findViewById(R.id.toolbar_DH);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerDonhang.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}