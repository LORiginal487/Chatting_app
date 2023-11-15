package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chatting_app.R;
import com.example.chatting_app.adapters.ChatAdapter;
import com.example.chatting_app.models.ChatMessage;
import com.example.chatting_app.models.User;
import com.example.chatting_app.utilities.Constants;
import com.example.chatting_app.utilities.ManagePreferences;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private User userTo;
    RecyclerView recyclerView;
    ProgressBar progressBar4;
    TextView nameOfToUser;
    EditText inMessageBox;
    private List<ChatMessage> chatMessages;
    private ManagePreferences managePreferences;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Log.d("ok", "33333333333");
        callViews();
        loadUser();
        callClasses();
        ListenMessages();
    }
    private void callViews(){
        nameOfToUser = findViewById(R.id.nameOfTouser);
        inMessageBox = findViewById(R.id.inText);
        recyclerView = findViewById(R.id.recycleV1cht);
        progressBar4 = findViewById(R.id.progressBar4);
    }
    private void loadUser(){
        userTo = (User) getIntent().getSerializableExtra(Constants.Key_User);
        nameOfToUser.setText(userTo.name);
    }
    private void callClasses(){
        managePreferences = new ManagePreferences(getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages,
                getImagePP(userTo.image),
                managePreferences.getString(Constants.Key_Users_Id));
        recyclerView.setAdapter(chatAdapter);
        dataBase = FirebaseFirestore.getInstance();

    }
    private void ListenMessages(){
        dataBase.collection(Constants.Key_Collection_Chat)
                .whereEqualTo(Constants.Key_Sender_ID, managePreferences.getString(Constants.Key_Users_Id))
                .whereEqualTo(Constants.Key_Receiver_ID, userTo.id)
                .addSnapshotListener(eventListener);
        dataBase.collection(Constants.Key_Collection_Chat)
                .whereEqualTo(Constants.Key_Sender_ID, userTo.id)
                .whereEqualTo(Constants.Key_Receiver_ID, managePreferences.getString(Constants.Key_Users_Id))
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener= (value, error) -> {
        if(error != null){
            return;
        }if(value != null){
            int count = chatMessages.size();
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderID = documentChange.getDocument().getString(Constants.Key_Sender_ID);
                    chatMessage.receiverID = documentChange.getDocument().getString(Constants.Key_Receiver_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.Key_Message);
                    chatMessage.dateTime = getRedableDate(documentChange.getDocument().getDate(Constants.Key_Timestamp));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.Key_Timestamp);
                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages,(obj1, obj2)  -> obj1.dateObject.compareTo(obj2.dateObject));
            if(count == 0){
                chatAdapter.notifyDataSetChanged();
            }else{
                System.out.println("ok we in 2");
                Log.d("ok", "1111111111111");
                chatAdapter.notifyItemRangeInserted(chatMessages.size(),chatMessages.size());
                recyclerView.smoothScrollToPosition(chatMessages.size()-1);
            }
            recyclerView.setVisibility(View.VISIBLE);
        }
        System.out.println("ok we in 1");
        Log.d("ok", "22222222222222222");
        progressBar4.setVisibility(View.GONE);

    };
    private Bitmap getImagePP(String encodedImg){
        byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public void Go_Back(View view) {
        onBackPressed();
    }

    public void pressSend(View view) {
        sendMessage();
    }
    private void sendMessage(){
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.Key_Sender_ID, managePreferences.getString(Constants.Key_Users_Id));
        message.put(Constants.Key_Receiver_ID, userTo.id);
        message.put(Constants.Key_Message, inMessageBox.getText().toString());
        message.put(Constants.Key_Timestamp, new Date());
        dataBase.collection(Constants.Key_Collection_Chat).add(message);
        inMessageBox.setText(null);
    }
    private String getRedableDate(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }
}