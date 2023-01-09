package com.manager.quanlybanhang.apdapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manager.quanlybanhang.Interface.ItemClickListener;
import com.manager.quanlybanhang.R;
import com.manager.quanlybanhang.Utils.Utils;
import com.manager.quanlybanhang.activity.ChitietActivity;
import com.manager.quanlybanhang.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.internal.Util;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> array;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_new,parent,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      SanPhamMoi sanPhamMoi = array.get(position);
      holder.txtten.setText(sanPhamMoi.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
      holder.txtgia.setText("Giá "+decimalFormat.format(sanPhamMoi.getGia())+" $");
      if(sanPhamMoi.getHinhanh().contains("http")){
          Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imghinhanh);
      }else {
         String hinh = Utils.BASE_URL+"images/"+sanPhamMoi.getHinhanh();//doi duong dan
          Glide.with(context).load(hinh).into(holder.imghinhanh);

      }


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean islongClick) {
                if (!islongClick){
                    //click
                    Intent intent = new Intent(context, ChitietActivity.class);
                    intent.putExtra("chitiet",sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       TextView txtten, txtgia ;
       ImageView imghinhanh;
       private ItemClickListener  itemClickListener;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            txtten = itemView.findViewById(R.id.item_new_ten);
            txtgia = itemView.findViewById(R.id.item_new_gia);
            imghinhanh = itemView.findViewById(R.id.item_new_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);

        }
    }
}
