package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatting_app.R;
import com.example.chatting_app.utilities.Constants;
import com.example.chatting_app.utilities.ManagePreferences;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ManagePreferences managePreferences;
    String userName, userSurname;
    RoundedImageView rImg;
    TextView namesShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ma3.a 333333", "_______________");
        //________________-*
        managePreferences = new ManagePreferences(getApplicationContext());
        callViews();
        loadUsersDetails();
        getToken();
        //________________-*
    }
    private void loadUsersDetails(){
        Log.d("ma3.a2 333333", "_______________");
        namesShow.setText(managePreferences.getString(Constants.Key_Name));
        byte[] bytes = Base64.decode(managePreferences.getString(Constants.Key_Image), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
        rImg.setImageBitmap(bitmap);

    }
    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::upDateToken);
    }
    private void upDateToken(String tkn){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.Key_Collection_Users).document(
                        managePreferences.getString(Constants.Key_Users_Id)
                );
        documentReference.update(Constants.Key_FCM_Token, tkn)
                //.addOnSuccessListener(unused -> showToast("Token updated!!!"))
                .addOnFailureListener(e -> showToast("unable to update token"));
    }
    public void sign_Out(View view) {
        showToast("Signing out...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.Key_Collection_Users).document(
                        managePreferences.getString(Constants.Key_Users_Id)
                );
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.Key_FCM_Token, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    managePreferences.clearPref();
                    Intent intent = new Intent(getApplicationContext(), Sign_In.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> showToast("unable to sign out"));
    }

    private void callViews() {
        Log.d("ma3.a1 333333", "_______________");
        rImg = findViewById(R.id.imgProfile);
        namesShow = findViewById(R.id.nameTXT);
    }
    private void showToast(String mssg){
        Toast.makeText(this, mssg, Toast.LENGTH_SHORT).show();
    }


    public void Open_UserActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), usersActivity.class);
        startActivity(intent);
    }
}