package com.example.toptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.toptop.Adapter.SoundAdapter;
import com.example.toptop.Models.SoundModel;
import com.example.toptop.editorVideo.PortraitCameraActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SoundActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private List<SoundModel> soundModelList = new ArrayList<>();
    private SoundAdapter soundAdapter;
    private ImageView btnSound;
    private AppCompatButton addMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        recyclerview = findViewById(R.id.recyclerView);
        addMusic = findViewById(R.id.AddMusic);
        addMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SoundActivity.this, upsoundActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        btnSound = (ImageView) findViewById(R.id.btnClose);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SoundActivity.this, PortraitCameraActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        LinearLayoutManager layoutManager3= new LinearLayoutManager(this);
        layoutManager3.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(new GridLayoutManager(this,1));
        LoadSound();

        soundAdapter = new SoundAdapter(soundModelList,getApplicationContext());
        recyclerview.setAdapter(soundAdapter);
        soundAdapter.notifyDataSetChanged();
    }

    private void LoadSound() {
        DatabaseReference soundRef = FirebaseDatabase.getInstance().getReference().child("SoundModel");
        soundRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soundModelList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SoundModel soundModel = snapshot.getValue(SoundModel.class);
                    soundModelList.add(soundModel);
                }
                SoundAdapter adapter = new SoundAdapter(soundModelList,getApplicationContext());
                recyclerview.setAdapter(adapter);
                soundAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SoundActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSoundData() {
        DatabaseReference soundRef = FirebaseDatabase.getInstance().getReference().child("SoundModel");
        soundRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soundModelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SoundModel soundModel = snapshot.getValue(SoundModel.class);
                    soundModelList.add(soundModel);
                }
                soundAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SoundActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }





    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SoundActivity.this, PortraitCameraActivity.class);
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}