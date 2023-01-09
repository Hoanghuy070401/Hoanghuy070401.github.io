package com.example.quanlybanhang.apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.model.DonHang;

import java.util.List;

public class DonHangApdapter extends RecyclerView.Adapter<DonHangApdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> donHangList;

    public DonHangApdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      DonHang donHang = donHangList.get(position);
      holder.txtdonhang.setText("Đơn Hàng:"+donHang.getId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
       //apdapter chitiet
        ChitietAdapter chitietAdapter = new ChitietAdapter(context,donHang.getItem());
        holder.recyclerChitiet.setLayoutManager(layoutManager);
        holder.recyclerChitiet.setAdapter(chitietAdapter);
        holder.recyclerChitiet.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView txtdonhang;
         RecyclerView recyclerChitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhangchitiet);
            recyclerChitiet = itemView.findViewById(R.id.recyclerView_chitietdonhang);
        }
    }
}
