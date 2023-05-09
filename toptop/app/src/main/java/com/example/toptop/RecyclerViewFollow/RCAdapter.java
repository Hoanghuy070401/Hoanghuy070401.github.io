package com.example.toptop.RecyclerViewFollow;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toptop.ChatActivity;
import com.example.toptop.MesengerActivity;
import com.example.toptop.Models.User;
import com.example.toptop.R;
import com.example.toptop.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RCAdapter extends RecyclerView.Adapter<RCViewHolders> {

    private List<User> userList;
    private Context context;

    public RCAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public RCViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_follower_items,parent,false);
        RCViewHolders rcv = new RCViewHolders(view);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewHolders holder, @SuppressLint("RecyclerView") int position) {
        holder.mName.setText(userList.get(position).getUser_name());
//        Glide.with(getApplicationContext()).load(userList.get(position).getPhotoUrl()).into(holder.mUserphoto);
      if (UserInformation.listFollowing.contains(userList.get(holder.getLayoutPosition()).getUser_id())){
          holder.mFollow.setText("Following");
          holder.Mess.setVisibility(View.VISIBLE);
      }else {
          holder.mFollow.setText("Follow");
      }
      holder.mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (holder.mFollow.getText().equals("Follow")){
                    holder.mFollow.setText("Following");
                    FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Following").child(userList.get(position).getUser_id()).setValue(true);
                    holder.Mess.setVisibility(View.VISIBLE);

                }else {
                    holder.mFollow.setText("Follow");
                    FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Following").child(userList.get(position).getUser_id()).removeValue();
                }
            }
        });
        holder.Mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id",userList.get(position).getUser_id());
                intent.putExtra("username",userList.get(position).getUser_name());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
