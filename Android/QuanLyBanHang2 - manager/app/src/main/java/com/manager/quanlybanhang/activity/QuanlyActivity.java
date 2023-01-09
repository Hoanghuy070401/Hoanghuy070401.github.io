package com.manager.quanlybanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.manager.quanlybanhang.R;

import soup.neumorphism.NeumorphCardView;

public class QuanlyActivity extends AppCompatActivity {
    ImageView them;
    Toolbar toolbar;
    RecyclerView recyclerControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly);
        initView();
        initControl();
        ActionToolBar();
    }

    private void initControl() {
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThemSPActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        them = findViewById(R.id.add_quanly);
        toolbar = findViewById(R.id.toolbarcontrol);
        recyclerControl = findViewById(R.id.recyclercontrol);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerControl.setHasFixedSize(true);
        recyclerControl.setLayoutManager(layoutManager);

    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}