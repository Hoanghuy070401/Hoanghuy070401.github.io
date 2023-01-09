package com.example.quanlybanhang.apdapter;


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
import com.example.quanlybanhang.Interface.ItemClickListener;
import com.example.quanlybanhang.R;
import com.example.quanlybanhang.activity.ChitietActivity;
import com.example.quanlybanhang.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

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
      Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imghinhanh);

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
