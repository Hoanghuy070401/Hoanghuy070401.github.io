package com.example.toptop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptop.MessengerModel;
import com.example.toptop.Models.User;
import com.example.toptop.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
   private Context context;
   private List<MessengerModel> messengerModelsList;

    public MessageAdapter(Context context) {
        this.context = context;
        messengerModelsList = new ArrayList<>();
    }

    public void add(MessengerModel messengerModel){
        messengerModelsList.add(messengerModel);
        notifyDataSetChanged();
    }

    public void clear(){
        messengerModelsList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesager_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         MessengerModel messengerModel = messengerModelsList.get(position);
         holder.mgs.setText(messengerModel.getMessage());
         if (messengerModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
             holder.main.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_700));
             holder.main.setBackground(context.getResources().getDrawable(R.drawable.text_messega));
             holder.mgs.setTextColor(ContextCompat.getColor(context, R.color.black));
         }else
         {
             holder.main.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
             holder.main.setBackground(context.getResources().getDrawable(R.drawable.text_mesager2));
             holder.mgs.setTextColor(ContextCompat.getColor(context, R.color.black));
         }

    }

    @Override
    public int getItemCount() {
        return messengerModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mgs ;
        private LinearLayout main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mgs = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.mainMessagerLayout);
        }
    }
}
