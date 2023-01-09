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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Update extends AppCompatActivity {
    final String DATABASE_NAME="quanlynhanvien.sqlite";
    SQLiteDatabase database;
    final int RESQUEST_TAKE_PHOTO =123;
    final int RESQUEST_CHOOSE_PHOTO =321;


    int id =-1;
    Button btnChonAnh, btnChupAnh,btnLuu,btnHuy;
    EditText editTen, editSDT,editDiachi,editTuoi, editCMND;
    ImageView anhdaidien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        run();
        pushinfor();
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

    private void pushinfor() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        try {
            database = DataBase.initDatabase(this,DATABASE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cursor cursor=database.rawQuery("SELECT * FROM NhanVien WHERE ID = ?",new String[] {id + ""});
            cursor.moveToFirst();
            String Name = cursor.getString(1);
            String SDT = cursor.getString(2);
            String Tuoi  = cursor.getString(3);
            int CMND = cursor.getInt(4);
            String Diachi = cursor.getString(5);
            byte[] priture = cursor.getBlob(6);
        Bitmap bitmap = BitmapFactory.decodeByteArray(priture,0,priture.length);
        anhdaidien.setImageBitmap(bitmap);
        editTen.setText(Name);
        editSDT.setText(SDT);
        editDiachi.setText(Diachi);
        editTuoi.setText(Tuoi);
        editCMND.setText(String.valueOf(CMND));

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

                update();

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

    private void update() {

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


        try {
            database = DataBase.initDatabase(this,"quanlynhanvien.sqlite");
        } catch (IOException e) {
            e.printStackTrace();
        }


        database.update("NhanVien",contV,"ID=?",new String[] { id + ""});
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
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }
}