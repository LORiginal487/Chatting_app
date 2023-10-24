package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatting_app.R;
import com.example.chatting_app.utilities.Constants;
import com.example.chatting_app.utilities.ManagePreferences;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Sign_In extends AppCompatActivity {
    private ManagePreferences managePreferences;
    EditText email_in,passw_in;
    TextView addImg;
    Button signIn_btn;
    ProgressBar loadingProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //______________________

        callViews();
        managePreferences = new ManagePreferences(getApplicationContext());
        if(managePreferences.getBoolean(Constants.Key_Is_Signed_In)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Log.d("1.0_sa 222222", "_______________");
            finish();
        }
        //______________________
    }
    private void callViews(){

        email_in = findViewById(R.id.email_in_SI);
        passw_in = findViewById(R.id.passw_in_SI);
        signIn_btn = findViewById(R.id.buttonSignIn);
        loadingProgressBar = findViewById(R.id.progressBar);
    }

    public void open_Sign_Up(View view) {
        Intent intent = new Intent(getApplicationContext(), Sign_Up.class);
        startActivity(intent);
    }

    public void open_App(View view) {
        if(validation()){
            signIn();
        }

    }
    private void signIn(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Log.d("firebase 222222", "_______________");
        database.collection(Constants.Key_Collection_Users)
                .whereEqualTo(Constants.Key_Email, email_in.getText().toString())
                .whereEqualTo(Constants.Key_Password, passw_in.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        managePreferences.putBoolean(Constants.Key_Is_Signed_In, true);
                        Log.d("mp1 222222", "_______________");
                        managePreferences.putString(Constants.Key_Users_Id, documentSnapshot.getId());
                        Log.d("mp2 222222", "_______________");
                        managePreferences.putString(Constants.Key_Name, documentSnapshot.getString(Constants.Key_Name));
                        Log.d("mp3 222222", "_______________");
                        managePreferences.putString(Constants.Key_Image, documentSnapshot.getString(Constants.Key_Image));
                        Log.d("mp4 222222", "_______________");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Log.d("sa 222222", "_______________");
                    }else{
                        loading(false);
                        showToast("Unable to sign in check details");
                    }
                });
    }
    private boolean validation(){
        if(email_in.getText().toString().trim().isEmpty()){
            showToast("Enter Email");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email_in.getText().toString()).matches()){
            showToast("Enter valid Email");
            return false;
        }
        else if(passw_in.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return false;
        }else{
            return true;
        }
    }

    private void showToast(String mssg){
        Toast.makeText(this, mssg, Toast.LENGTH_SHORT).show();
    }
    private void loading(boolean isLoading){
        Log.d("l3 222222", "_______________");
        if(isLoading){
            signIn_btn.setVisibility(View.INVISIBLE);
            loadingProgressBar.setVisibility(View.VISIBLE);
            Log.d("l4 222222", "_______________");
        }else{
            signIn_btn.setVisibility(View.VISIBLE);
            loadingProgressBar.setVisibility(View.INVISIBLE);
            Log.d("l5 222222", "_______________");
        }

    }

}