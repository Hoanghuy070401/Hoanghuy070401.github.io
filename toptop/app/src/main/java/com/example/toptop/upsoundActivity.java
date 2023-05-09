package com.example.toptop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class upsoundActivity extends AppCompatActivity {

    private TextView nameMusic;
    private Button chosseMusic, uploadSound;
    private ProgressDialog progressDialog;
    private  String audioFileName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upsound);
        anhxa();
    }

    private void anhxa() {
        nameMusic =  findViewById(R.id.editName);
        chosseMusic= findViewById(R.id.chossemusic);
        uploadSound = findViewById(R.id.upload);

        chosseMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosevideo();
            }
        });
        uploadSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadvideo();
                Intent intent = new Intent(upsoundActivity.this, SoundActivity.class);
                startActivity(intent);
            }
        });

    }

    public String getTimeCurrent() {
        LocalDateTime dateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.now();
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        }
        String formattedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDateTime = dateTime.format(formatter);
        }
        return formattedDateTime;
    }
    public static long convertToMiliSecond(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date date = format.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // choose a video from phone storage
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }

    Uri sounduri;

    // startActivityForResult được sử dụng để nhận kết quả là video đã chọn.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            sounduri = data.getData();
            if (sounduri != null) {
                Cursor cursor = getContentResolver().query(sounduri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        audioFileName = cursor.getString(nameIndex);
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
            if (audioFileName == null) {
                // nếu không lấy được tên file, thì lấy tên theo đường dẫn
                audioFileName = new File(sounduri.getPath()).getName();
            }
            nameMusic.setText(audioFileName);
        }
    }

    private String getfiletype(Uri sounduri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp3
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(sounduri));

    }
    private boolean check_uploadvideo = false;

    private boolean uploadvideo() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        if (sounduri != null) {
            // save the selected video in Firebase storage
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Files/Sound/"+ FirebaseAuth.getInstance().getUid()+"/"+ + System.currentTimeMillis() + "." + getfiletype(sounduri
            ));
            reference.putFile(sounduri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("SoundModel");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("sound_sound", downloadUri);
                    map.put("soundid", reference1.push().getKey());
                    map.put("sound_title",nameMusic.getText().toString());
                    map.put("datePost",getTimeCurrent());
                    reference1.child("" + System.currentTimeMillis()).setValue(map);
                    // Video uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(upsoundActivity.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                    check_uploadvideo = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(upsoundActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
        return check_uploadvideo;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(upsoundActivity.this, SoundActivity.class);
        startActivity(intent);
    }
}