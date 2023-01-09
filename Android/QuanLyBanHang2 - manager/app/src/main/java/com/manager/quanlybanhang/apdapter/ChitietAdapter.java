package com.manager.quanlybanhang.apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manager.quanlybanhang.R;
import com.manager.quanlybanhang.model.Item;

import java.text.DecimalFormat;
import java.util.List;

public class ChitietAdapter extends RecyclerView.Adapter<ChitietAdapter.MyViewHolder> {
     List<Item> itemList;
     Context context;

    public ChitietAdapter( Context context,List<Item> itemList) {
        this.itemList = itemList;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Item item = itemList.get(position);
        holder.txtten.setText(item.getTen()+" ");
        holder.txtsoluong.setText("Số Lượng : "+item.getSoluong()+" ");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### "+"$");
        holder.txtGia.setText("Giá Tiền: "+decimalFormat.format(item.getGia()));
        Glide.with(context).load(item.getHinhanh()).into(holder.imagechitietdonhang);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagechitietdonhang;
        TextView txtten, txtsoluong,txtGia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechitietdonhang =(ImageView) itemView.findViewById(R.id.item_imgchitiet);
            txtten= (TextView) itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong=(TextView) itemView.findViewById(R.id.item_soluongchitiet);
            txtGia=(TextView) itemView.findViewById(R.id.item_giachitiet);

        }
    }
}
