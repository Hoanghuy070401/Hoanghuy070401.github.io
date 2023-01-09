package com.example.quanlybanhang.apdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlybanhang.Interface.imageClick;
import com.example.quanlybanhang.R;
import com.example.quanlybanhang.Utils.Utils;
import com.example.quanlybanhang.model.EvenBus.TinhTongEvent;
import com.example.quanlybanhang.model.GioHang;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      GioHang gioHang = gioHangList.get(position);
      holder.Item_giohang_Ten.setText(gioHang.getTen());
      holder.Item_giohang_soluong.setText(String.valueOf(gioHang.getSoluong()+" "));
      DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
      holder.Item_giohang_gia.setText(decimalFormat.format(gioHang.getGia())+" $");
      Glide.with(context).load(gioHang.getHinhsp()).into(holder.Item_giohang_image);
        System.out.println(gioHang.getGia()+"hihi");
      long gia = gioHang.getSoluong()*(gioHang.getGia());
      holder.Item_giohang_giasp2.setText(decimalFormat.format(gia)+"$");
      holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             if (b){
               Utils.mangmuahang.add(gioHang);
               EventBus.getDefault().postSticky(new TinhTongEvent());
             }else {
                 for (int i =0 ;i<Utils.mangmuahang.size();i++){
                     if (Utils.mangmuahang.get(i).getIdsp()==gioHang.getIdsp()){
                         Utils.mangmuahang.remove(i);
                         EventBus.getDefault().postSticky(new TinhTongEvent());
                     }
                 }
             }
          }
      });
      holder.setListenner(new imageClick() {
          @Override
          public void onImageClick(View view, int pos, int giatri) {
              if (giatri==1){
                  if(gioHangList.get(pos).getSoluong()>1){
                      int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                      gioHangList.get(pos).setSoluong(soluongmoi);

                      holder.Item_giohang_soluong.setText(String.valueOf(gioHangList.get(pos).getSoluong()+" "));
                      long gia = (gioHangList.get(pos).getSoluong())*(gioHangList.get(pos).getGia());
                      holder.Item_giohang_giasp2.setText(decimalFormat.format(gia)+"$");
                      EventBus.getDefault().postSticky(new TinhTongEvent());
                  }else if(gioHangList.get(pos).getSoluong()==1){
                      AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                      builder.setTitle("Thông Báo");
                      builder.setMessage("Bạn Có Muốn Xóa Sản Phẩm Này Khỏi Giỏ Hàng");
                      builder.setPositiveButton("Đồng Ý ", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              Utils.manggiohang.remove(pos);
                              notifyDataSetChanged();
                              EventBus.getDefault().postSticky(new TinhTongEvent());
                          }
                      });
                      builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                          }
                      });
                    builder.show();
                  }
              }else if (giatri==2){
                  if(gioHangList.get(pos).getSoluong()<11){
                      int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                      gioHangList.get(pos).setSoluong(soluongmoi);
                  }
                  holder.Item_giohang_soluong.setText(String.valueOf(gioHangList.get(pos).getSoluong()+" "));
                  long gia = (gioHangList.get(pos).getSoluong())*(gioHangList.get(pos).getGia());
                  holder.Item_giohang_giasp2.setText(decimalFormat.format(gia)+"$");
                  EventBus.getDefault().postSticky(new TinhTongEvent());

              }
               }
      });

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         ImageView Item_giohang_image,imgcong,imgtru;
         TextView Item_giohang_Ten,Item_giohang_gia,Item_giohang_soluong,Item_giohang_giasp2;
         imageClick listenner ;
         CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Item_giohang_image = (ImageView) itemView.findViewById(R.id.item_giohang_image);
            Item_giohang_Ten = (TextView) itemView.findViewById(R.id.item_giohang_tensp);
            Item_giohang_gia  = (TextView) itemView.findViewById(R.id.item_giohang_gia);
            Item_giohang_soluong = (TextView) itemView.findViewById(R.id.item_giohang_soluong);
            Item_giohang_giasp2 = (TextView) itemView.findViewById(R.id.item_gioHang_giaSp2);
            imgcong = (ImageView) itemView.findViewById(R.id.item_giohang_cong);
            imgtru =(ImageView) itemView.findViewById(R.id.item_giohang_tru);
            checkBox = itemView.findViewById(R.id.item_giohang_checklist);
            //event Click
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }


        public void setListenner(imageClick listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru){
                //1 tru
              listenner.onImageClick(view,getAdapterPosition(),1);
            }else if(view == imgcong){
               //2 cong
                listenner.onImageClick(view,getAdapterPosition(),2);
            }
        }
    }
}
