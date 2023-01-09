package com.example.quanlybanhang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class addActivity extends AppCompatActivity {
    final String DATABASE_NAME="quanlynhanvien.sqlite";
    SQLiteDatabase database;
    final int RESQUEST_TAKE_PHOTO =123;
    final int RESQUEST_CHOOSE_PHOTO =321;



    Button btnChonAnh, btnChupAnh,btnLuu,btnHuy;
    EditText editTen, editSDT,editDiachi,editTuoi, editCMND;
    ImageView anhdaidien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        run();
        addEvent();
    }
    private void run(){
        btnChonAnh = (Button) findViewById(R.id.btnchoose);
        btnChupAnh = (Button) findViewById(R.id.btntake);
        btnLuu = (Button) findViewById(R.id.btnsave);
        btnHuy = (Button) findViewById(R.id.btncancel);
        editTen = (EditText) findViewById(R.id.editName);
        editSDT=(EditText) findViewById(R.id.editSDT);
        editDiachi = (EditText) findViewById(R.id.editDiachi);
        editTuoi =(EditText)findViewById(R.id.edittuoi);
        editCMND =(EditText)findViewById(R.id.editCmnd);
        anhdaidien =(ImageView) findViewById(R.id.imganhedit);
    }


    private void addEvent(){
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePhoto();
            }
        });
        btnChupAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    insert();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

    }



    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,RESQUEST_TAKE_PHOTO);
    }
    private void ChoosePhoto (){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,RESQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    anhdaidien.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                anhdaidien.setImageBitmap(bitmap);
            }
        }
    }

    private void insert() throws IOException {

        String ten = editTen.getText().toString();
        String sdt = editSDT.getText().toString() ;
        String tuoi =editTuoi.getText().toString();
        String diachi = editDiachi.getText().toString();
        String cmnd =String.valueOf(editCMND.getText().toString());
        byte[] anh = getByteArrayFromImageView(anhdaidien);

        ContentValues contV= new ContentValues();

        contV.put("Name",ten);
        contV.put("SDT",sdt);
        contV.put("Tuoi",tuoi);
        contV.put("CMND",cmnd);
        contV.put("DiaChi",diachi);
        contV.put("Priture",anh);


        SQLiteDatabase database = DataBase.initDatabase(this,"quanlynhanvien.sqlite");


        database.insert("NhanVien",null,contV);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    private void cancel(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private   byte[] getByteArrayFromImageView(ImageView imgv){
        BitmapDrawable drawable= (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,10000,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }
}