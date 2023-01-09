package com.manager.quanlybanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.manager.quanlybanhang.R;
import com.manager.quanlybanhang.Utils.Utils;
import com.manager.quanlybanhang.apdapter.SanPhamApdater;
import com.manager.quanlybanhang.model.SanPhamMoi;
import com.manager.quanlybanhang.retrofit.ApiBanHang;
import com.manager.quanlybanhang.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    int page = 1;
    int loai;
    SanPhamApdater apdaterDt;
    List<SanPhamMoi> sanPhamList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isloading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loai = getIntent().getIntExtra("loai",1);

        getData(page);
        ketnoi();
        ActionToolBar();
        addEventLoad();
        toolbar.setTitle("LapTop");
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isloading==false ){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition()== sanPhamList.size()-1){
                        isloading=true;
                        loadMore();
                    }
                }
            }
        });
    }
    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //addnull

                sanPhamList.add(null);
                apdaterDt.notifyItemInserted(sanPhamList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remover null
                sanPhamList.remove(sanPhamList.size()-1);
                apdaterDt.notifyItemRemoved(sanPhamList.size());
                page =page+1;
                getData(page);
                apdaterDt.notifyDataSetChanged();
                isloading= false;
            }
        },2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getSanpham(page,loai)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                if (apdaterDt == null){
                                    sanPhamList= sanPhamMoiModel.getResult();
                                    apdaterDt = new SanPhamApdater(LaptopActivity.this,sanPhamList);
                                    recyclerView.setAdapter(apdaterDt);
                                }else {
                                    int vitri = sanPhamList.size()-1;
                                    int soluongadd = sanPhamMoiModel.getResult().size();
                                    for (int i =0;i<soluongadd;i++){
                                        sanPhamList.add(sanPhamMoiModel.getResult().get(i));
                                    }
                                    apdaterDt.notifyItemRangeInserted(vitri,soluongadd);
                                }
                            }else {
                                 //Toast.makeText(LaptopActivity.this, "Đã Hết Sản Phẩm", Toast.LENGTH_SHORT).show();
                                isloading=true;
                            }

                        },
                        throwable -> {
                           // Toast.makeText(LaptopActivity.this, "Không kết nối được server", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void ketnoi() {
        toolbar = findViewById(R.id.toolbar_Dt);
        recyclerView = findViewById(R.id.recycler_dt);
        linearLayoutManager = new LinearLayoutManager(LaptopActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        sanPhamList = new ArrayList<>();


    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}