package com.example.toptop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptop.Models.SoundModel;
import com.example.toptop.R;
import com.example.toptop.editorVideo.PortraitCameraActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    List<SoundModel> soundModelList;
    Context context;

    public SoundAdapter(List<SoundModel> soundModelList, Context context) {
        this.soundModelList = soundModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sound,parent,false);


        return new SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position) {
        DatabaseReference soundRef = FirebaseDatabase.getInstance().getReference().child("SoundModel");
        soundRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SoundModel soundModel = snapshot.getValue(SoundModel.class);
                    holder.nameSound.setText(soundModel.getSound_title());
                    holder.loaiSound.setText(soundModel.getSound_sound());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, PortraitCameraActivity.class);
                            intent.putExtra("sound_sound",soundModel.getSound_sound());
                            intent.putExtra("sound_title",soundModel.getSound_title());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });


    }

    @Override
    public int getItemCount() {
        return soundModelList.size();
    }

    public class SoundViewHolder extends RecyclerView.ViewHolder {
        private TextView nameSound;
        private TextView loaiSound;
        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            nameSound = itemView.findViewById(R.id.sound_title);
            loaiSound = itemView.findViewById(R.id.sound_sound);
        }
    }
}
