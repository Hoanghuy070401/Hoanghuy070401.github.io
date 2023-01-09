package com.example.quanlybanhang;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterNhanVien extends BaseAdapter {
    Activity context;
    ArrayList<NhanVien> listNV;

    public AdapterNhanVien(Activity context, ArrayList<NhanVien> listNV) {
        this.context = context;
        this.listNV = listNV;
    }

    @Override
    public int getCount() {
        return listNV.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listviewnhanvien,null,false);

        ImageView imgNV = (ImageView) row.findViewById(R.id.imgNV);
        TextView txtid = (TextView) row.findViewById(R.id.txtid);
        TextView txtname = (TextView) row.findViewById(R.id.txtName);
        TextView txttuoi = (TextView) row.findViewById(R.id.txttuoi);
        TextView txtcmnd = (TextView) row.findViewById(R.id.txtCMND);
        TextView txtdiachi = (TextView) row.findViewById(R.id.txtDiachi);
        TextView txtsdt = (TextView) row.findViewById(R.id.txtSDT);
        Button btnsua =(Button) row.findViewById(R.id.btnSua);
        Button btnxoa =(Button) row.findViewById(R.id.btnXoa);

        NhanVien nhanVien= listNV.get(i);

        txtid.setText(String.valueOf(nhanVien.Id));
        txtname.setText(nhanVien.Name);
        txttuoi.setText(nhanVien.Tuoi);
        txtsdt.setText(nhanVien.SDT);
        txtcmnd.setText(String.valueOf(nhanVien.CMND));
        txtdiachi.setText(nhanVien.DiaChi);

        Bitmap bmanh = BitmapFactory.decodeByteArray(nhanVien.priture, 0,nhanVien.priture.length);
        imgNV.setImageBitmap(bmanh);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Update.class);
                intent.putExtra("ID",nhanVien.Id);
                context.startActivity(intent);

            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("xac nhan xoa");
                builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            delete(nhanVien.Id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        return row;
    }
    private void delete(int idNhanVien) throws IOException {
        SQLiteDatabase database= DataBase.initDatabase(context,"quanlynhanvien.sqlite");
        database.delete("NhanVien","ID=?",new String[]{idNhanVien+""});
        listNV.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien",null);
        while (cursor.moveToNext()){
            int id =cursor.getInt(0);
            String Name = cursor.getString(1);
            String SDT = cursor.getString(2);
            String Tuoi =cursor.getString(3);
            int CMND =cursor.getInt(4);
            String DiaChi = cursor.getString(5);
            byte[] priture = cursor.getBlob(6);
           listNV.add(new NhanVien(id,Name,SDT,Tuoi,CMND,DiaChi,priture));
        }
        notifyDataSetChanged();
    }
}
