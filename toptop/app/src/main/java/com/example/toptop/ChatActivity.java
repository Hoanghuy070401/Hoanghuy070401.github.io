package com.example.toptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.toptop.Adapter.MessageAdapter;
import com.example.toptop.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    String recieverId,usetName;
    DatabaseReference databaseReferenceSender,databaseReferenceReciever;
    String senderRoom ,recieverRoom;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recieverId=getIntent().getStringExtra("id");
        usetName=getIntent().getStringExtra("username");

        senderRoom = FirebaseAuth.getInstance().getUid()+recieverId;
        recieverRoom = recieverId+FirebaseAuth.getInstance().getUid();
        messageAdapter = new MessageAdapter(this );
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));


        binding.toobar.setTitle(usetName);
        setSupportActionBar(binding.toobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReciever = FirebaseDatabase.getInstance().getReference("chats").child(recieverRoom);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessengerModel messengerModel = dataSnapshot.getValue(MessengerModel.class);
                    messageAdapter.add(messengerModel);
                }
                //sat lại vi trí đoạn chat
                binding.recycler.smoothScrollToPosition(messageAdapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.messengeredit.getText().toString();
                if (message.trim().length()>0){
                    sendMessage(message);
                    binding.messengeredit.setText("");
                }
            }
        });

    }

    private void sendMessage(String message) {
        String messageId= UUID.randomUUID().toString();
        MessengerModel messengerModel = new MessengerModel(messageId,FirebaseAuth.getInstance().getUid(),message);

        messageAdapter.add(messengerModel);
        databaseReferenceSender.child(messageId).setValue(messengerModel);
        databaseReferenceReciever.child(messageId).setValue(messengerModel);
    }
}