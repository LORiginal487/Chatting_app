package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatting_app.R;

public class Sign_In extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

    }

    public void open_Sign_Up(View view) {
        Intent intent = new Intent(Sign_In.this, Sign_Up.class);
        startActivity(intent);
    }

    public void open_App(View view) {
    }
}