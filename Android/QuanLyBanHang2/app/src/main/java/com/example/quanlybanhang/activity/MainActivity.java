package com.example.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.quanlybanhang.R;
import com.example.quanlybanhang.Utils.Utils;
import com.example.quanlybanhang.apdapter.LoaiSpAdapter;
import com.example.quanlybanhang.apdapter.SanPhamMoiAdapter;
import com.example.quanlybanhang.model.LoaiSp;
import com.example.quanlybanhang.model.SanPhamMoi;
import com.example.quanlybanhang.model.SanPhamMoiModel;
import com.example.quanlybanhang.model.User;
import com.example.quanlybanhang.retrofit.ApiBanHang;
import com.example.quanlybanhang.retrofit.RetrofitClient;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listViewMHC;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter ;
    List<LoaiSp> mangloaisp;
   CompositeDisposable compositeDisposable = new CompositeDisposable();
   ApiBanHang apiBanHang;
   List<SanPhamMoi> mangSpMoi;
   SanPhamMoiAdapter spAdapter;
   NotificationBadge badge;
   FrameLayout frameLayout;
   ImageView imgSeach;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init(this);
//      if (Paper.book().read("user")!=null){
//            User user = Paper.book().read("email");
//            Utils.user_current= user;
//        }


        Ketnoi();
        ActionBar();

        if (isConnected(this)){
            ActionViewFliper();
            getLoaiSanPham();
            getSpMoi();
            getEventClick();

        }else{
            Toast.makeText(this, "vui long kết nối internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void getEventClick() {
        listViewMHC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent laptop = new Intent(getApplicationContext(),LaptopActivity.class);
                        laptop.putExtra("loai",1);
                        startActivity(laptop);
                        break;
                    case 2:
                        Intent dienthoai = new Intent(getApplicationContext(),DienThoaiActivity.class);
                        dienthoai.putExtra("loai",2);
                        startActivity(dienthoai);
                        break;
                    case 5:
                        Intent donhang = new Intent(getApplicationContext(),XemDonActivity.class);
                        startActivity(donhang);
                        break;

                    case 6:
                        Paper.book().delete("isLogin");

                        Intent dangxuat = new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(dangxuat);
                        finish();
                        break;
                }
            }
        });
    }

    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                          if (sanPhamMoiModel.isSuccess()){
                              mangSpMoi = sanPhamMoiModel.getResult();
                              spAdapter = new SanPhamMoiAdapter(getApplicationContext(),mangSpMoi);
                              recyclerView.setAdapter(spAdapter);
                          }
                        },
                        throwable -> {
                            Toast.makeText(this, "No Connect", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaisp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()){
                             mangloaisp = loaiSpModel.getResult();
                             mangloaisp.add(new LoaiSp("Đăng Xuất ","data:image/jpeg;base64,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"));
                             loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangloaisp);
                                listViewMHC.setAdapter(loaiSpAdapter);
                                }
                        }
                ));

    }

    private void ActionViewFliper() {
        List<String> mangAD= new ArrayList<>();
        mangAD.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        mangAD.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangAD.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        for (int i=0 ; i<mangAD.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangAD.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Ketnoi() {
        toolbar =(Toolbar) findViewById(R.id.ToolbarMHC);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        badge =(NotificationBadge)findViewById(R.id.menu_s1);
        frameLayout = (FrameLayout)findViewById(R.id.framegioang);
        imgSeach = (ImageView)findViewById(R.id.imgseach);

        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        listViewMHC = (ListView) findViewById(R.id.listViewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //khoi tao list
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        if (Utils.manggiohang==null){
            Utils.manggiohang=new ArrayList<>();
        }else {
                int totalItem = 0;
                for (int i =0 ; i<Utils.manggiohang.size();i++){
                    totalItem = totalItem+Utils.manggiohang.get(i).getSoluong();
                }

                badge.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });

        imgSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seach = new Intent(getApplicationContext(),SeachSPActivity.class);
                startActivity(seach);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
            int totalItem = 0;
            for (int i =0 ; i<Utils.manggiohang.size();i++){
                totalItem = totalItem+Utils.manggiohang.get(i).getSoluong();
            }

            badge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);//cap quyen ben file android de ko loi
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ( (wifi!=null && wifi.isConnected()) ||(mobile!=null && mobile.isConnected()) ){
          return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}