package com.example.toptop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toptop.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    private CircleImageView imageUser;
    private ImageView btnchageImg;
    private TextView userName;
    private AppCompatButton btnFollow,btnOut;
    User user ;
    FirebaseUser firebase;
    Uri imageURI;
    String imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        anhxa();

    }

    private void anhxa() {
        imageUser = findViewById(R.id.image_user);
        userName = findViewById(R.id.txtUserName);
        btnFollow = findViewById(R.id.btnFolloww);
        btnOut = findViewById(R.id.btnsignOut);
        btnchageImg= findViewById(R.id.btnaccount);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = databaseRef.child("User");
        usersRef.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    User us = snapshot.getValue(User.class);
                    Glide.with(getApplicationContext()).load(us.getPhotoUrl()).into(imageUser);
                    userName.setText("@"+us.getUser_name());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent( AccountActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null)
        {
            btnFollow.setVisibility(View.GONE);
        }else
        {
            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (btnFollow.getText().equals("Follow")){
                        btnFollow.setText("Following");
                        FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Following").child(user.getUser_id()).setValue(true);


                    }else {
                        btnFollow.setText("Follow");
                        FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Following").child(user.getUser_id()).removeValue();
                    }
                }
            });
        }
        btnchageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chosse image "),10);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10)
        {
           if (data != null)
           {
              imageURI = data.getData();
               FirebaseStorage storage = FirebaseStorage.getInstance();
               StorageReference storageRef = storage.getReference(user.getUser_id()).child("User").child(user.getPhotoUrl());
               storageRef.putFile(imageURI)
                       .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               // Tải ảnh mới lên thành công
                               // Lấy đường dẫn đến ảnh mới và lưu vào Realtime Database
                               storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       // Lấy đường dẫn đến ảnh mới và lưu vào Realtime Database
                                       String imageUrl = uri.toString();
                                       DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(user.getUser_id()).child(user.getPhotoUrl());
                                       databaseRef.child(user.getPhotoUrl()).setValue(imageUrl);
                                   }
                               });
                               imageUser.setImageURI(imageURI);
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception exception) {
                               // Tải ảnh mới lên thất bại
                               Toast.makeText(AccountActivity.this, "Upload Fail", Toast.LENGTH_SHORT).show();
                           }
                       });

           }


        }
    }

    public void messengerPage(View view){
        Intent intent = new Intent(AccountActivity.this,MesengerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
    public void discoverPage(View view){
        Intent intent = new Intent(AccountActivity.this,DiscoverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
    public void homePage(View view){
        Intent intent = new Intent(AccountActivity.this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccountActivity.this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}