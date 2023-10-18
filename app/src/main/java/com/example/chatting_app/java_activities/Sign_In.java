package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatting_app.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

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