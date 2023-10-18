package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatting_app.R;

public class Sign_Up extends AppCompatActivity {
    String image, email_c = "no", passw_c = "no";
    EditText name_in, email_in, email_in_c, passw_in, passw_in_c, surname_in;
    Button signUp_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        callViews();
    }

    public void open_Sign_In(View view) {
        Intent intent = new Intent(Sign_Up.this, Sign_In.class);
        startActivity(intent);
    }

    public void open_App(View view) {
        if(checkInputs()){
            signUpMeth();
        }
    }
    private void showToast(String mssg){
        Toast.makeText(this, mssg, Toast.LENGTH_SHORT).show();
    }
    private boolean checkInputs(){
        if(image == null){
            showToast("Please add image");
            return false;
        }else if(name_in.getText().toString().trim().isEmpty()){
            showToast("Please enter name");
            name_in.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if(surname_in.getText().toString().trim().isEmpty()){
            showToast("Please enter name");
            surname_in.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if(email_in.getText().toString().trim().isEmpty()){

            showToast("Please enter name");
            email_in.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if(passw_in.getText().toString().trim().isEmpty()){
            showToast("Please enter name");
            passw_in.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }else if(email_in_c.getText().toString().trim().isEmpty()){
            showToast("Please confirm email");
            email_in_c.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if(passw_in_c.getText().toString().trim().isEmpty()){
            showToast("Please confirm password");
            passw_in_c.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if (email_in_c.getText().toString().equalsIgnoreCase(email_in.getText().toString())) {
            showToast("Please confirm email");
            email_in_c.setTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if (passw_in_c.getText().toString().equals(passw_in.getText().toString())) {
            showToast("Please confirm email");
            passw_in_c.setTextColor(Integer.parseInt("@colors/red"));
            return false;
        }else{
            return true;
        }
    }
    public void callViews(){
        name_in = findViewById(R.id.name_in);
        email_in = findViewById(R.id.email_in);
        email_in_c = findViewById(R.id.email_c_in);
        passw_in_c = findViewById(R.id.Password_c_in);
        passw_in = findViewById(R.id.Password_in);
        surname_in = findViewById(R.id.surname_in);
    }
    public void signUpMeth(){

    }

}