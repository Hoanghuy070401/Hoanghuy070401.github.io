package com.example.toptop.RecyclerViewFollow;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toptop.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RCViewHolders extends RecyclerView.ViewHolder {
    public TextView mName;
    public Button mFollow;
    public ImageView Mess;
    public CircleImageView mUserphoto;

    public RCViewHolders(View itemView){
        super(itemView);
        mName = itemView.findViewById(R.id.username);
        mFollow = itemView.findViewById(R.id.btnfollow);
        mUserphoto = itemView.findViewById(R.id.user_photo);
        Mess = itemView.findViewById(R.id.btnmess);
    }
}
