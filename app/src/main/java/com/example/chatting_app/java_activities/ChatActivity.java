package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.chatting_app.R;
import com.example.chatting_app.models.User;
import com.example.chatting_app.utilities.Constants;

public class ChatActivity extends AppCompatActivity {
    private User userTo;
    TextView nameOfTouser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        callViews();
        loadUser();
    }
    private void callViews(){
        nameOfTouser = findViewById(R.id.nameOfTouser);
    }
    private void loadUser(){
        userTo = (User) getIntent().getSerializableExtra(Constants.Key_User);
        nameOfTouser.setText(userTo.name);
    }
    public void Go_Back(View view) {
        onBackPressed();
    }

    public void pressSend(View view) {
    }
}