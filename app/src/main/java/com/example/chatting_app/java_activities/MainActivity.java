package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatting_app.R;
import com.example.chatting_app.utilities.Constants;
import com.example.chatting_app.utilities.ManagePreferences;
import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity {
    private ManagePreferences managePreferences;
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

        //________________-*
    }
    private void loadUsersDetails(){
        Log.d("ma3.a2 333333", "_______________");
        namesShow.setText(managePreferences.getString(Constants.Key_Name));
        byte[] bytes = Base64.decode(managePreferences.getString(Constants.Key_Image), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
        rImg.setImageBitmap(bitmap);
    }
    private void upDateToken(String tkn){
        
    }

    private void callViews() {
        Log.d("ma3.a1 333333", "_______________");
        rImg = findViewById(R.id.imgProfile);
        namesShow = findViewById(R.id.nameTXT);
    }
    private void showToast(String mssg){
        Toast.makeText(this, mssg, Toast.LENGTH_SHORT).show();
    }

}