package com.example.qlnv1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterNhanVien extends BaseAdapter {
    Context context;
    ArrayList<NhanVien> list ;

    public AdapterNhanVien(Context context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//truyen layout cho listview
        View row = inflater.inflate(R.layout.listview,null);//cho null chi view group
        //anh xa
        ImageView imgHinhDaiDien = (ImageView) row.findViewById(R.id.imgHinhDaiDien);
        TextView txtId = (TextView) row.findViewById(R.id.txtID);
        TextView txtName = (TextView) row.findViewById(R.id.txtName);
        TextView txtSDT = (TextView) row.findViewById(R.id.txtSDT);
        TextView txtDiaChi = (TextView) row.findViewById(R.id.txtDiachi);
        Button btnSua = (Button) row.findViewById(R.id.btnsua);
        Button btnXoa = (Button) row.findViewById(R.id.btnxoa);

        NhanVien nhanvien = list.get(position);//set list gia tri tren giao dien
        //set thong tin tren tung thanh phan
        txtId.setText(nhanvien.ID+" ");
        txtName.setText(nhanvien.Name);
        txtSDT.setText(nhanvien.SDT);
        txtDiaChi.setText(nhanvien.Diachi);

        //decode hinh thanh bitmap vaf sau do set len giao dien
        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(nhanvien.picture,0,nhanvien.picture.length);
        imgHinhDaiDien.setImageBitmap(bmHinhDaiDien);
        return row;
    }
}
