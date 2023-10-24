package com.example.chatting_app.java_activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatting_app.R;
import com.example.chatting_app.utilities.Constants;
import com.example.chatting_app.utilities.ManagePreferences;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class Sign_Up extends AppCompatActivity {
    private String image, email_c = "no", passw_c = "no";
    private ManagePreferences managePreferences;
    EditText name_in, email_in, email_in_c, passw_in, passw_in_c, surname_in;
    TextView addImg;
    Button signUp_btn;
    ProgressBar loadingProgressBar;
    ImageView imageProf;
    FrameLayout imageLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //________________-*
        managePreferences = new ManagePreferences(getApplicationContext());
        callViews();


        //________________-*
    }

    public void open_Sign_In(View view) {
        Intent intent = new Intent(getApplicationContext(), Sign_In.class);
        startActivity(intent);
    }

    public void open_App(View view) {
        if(checkInputs()){
            signUpMeth();
        }
    }
    private boolean checkInputs(){
        if(image == null){
            showToast("Please add image");
            return false;
        }else if(name_in.getText().toString().trim().isEmpty()){
            showToast("Please enter name");
            //name_in.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if(surname_in.getText().toString().trim().isEmpty()){
            showToast("Please enter name");
            //surname_in.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if(email_in.getText().toString().trim().isEmpty()){

            showToast("Please enter name");
            //email_in.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if(passw_in.getText().toString().trim().isEmpty()){
            showToast("Please enter name");
            //passw_in.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }else if(email_in_c.getText().toString().trim().isEmpty()){
            showToast("Please confirm email");
            //email_in_c.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if(passw_in_c.getText().toString().trim().isEmpty()){
            showToast("Please confirm password");
            //passw_in_c.setHintTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if (!email_in_c.getText().toString().equalsIgnoreCase(email_in.getText().toString())) {
            showToast("Please confirm email");
            //email_in_c.setTextColor(Integer.parseInt("@colors/red"));
            return false;
        }
        else if (!passw_in_c.getText().toString().equals(passw_in.getText().toString())) {
            showToast("Please confirm email");
            //passw_in_c.setTextColor(Integer.parseInt("@colors/red"));
            return false;
        }else{
            return true;
        }
    }
    private void callViews(){
        name_in = findViewById(R.id.name_in);
        email_in = findViewById(R.id.email_in);
        email_in_c = findViewById(R.id.email_c_in);
        passw_in_c = findViewById(R.id.Password_c_in);
        passw_in = findViewById(R.id.Password_in);
        surname_in = findViewById(R.id.surname_in);
        signUp_btn = findViewById(R.id.buttonSignUp);
        loadingProgressBar = findViewById(R.id.progressBar);
        imageProf = findViewById(R.id.image_In);
        imageLay = findViewById(R.id.layoutImageIn);
        addImg = findViewById(R.id.addImageTxt);
    }
    private void signUpMeth(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Log.d("firebase 1111111", "_______________");
        HashMap<String, Object> user = new HashMap<>();
        Log.d("hash map 1111111", "_______________");
        user.put(Constants.Key_Name, name_in.getText().toString());
        Log.d("c1 1111111", "_______________");
        user.put(Constants.Key_Surname, surname_in.getText().toString());
        Log.d("c2 1111111", "_______________");
        user.put(Constants.Key_Email, email_in.getText().toString());
        Log.d("c3 1111111", "_______________");
        user.put(Constants.Key_Password, passw_in.getText().toString());
        Log.d("c4 1111111", "_______________");
        user.put(Constants.Key_Image, image);
        Log.d("c5 1111111", "_______________");
        database.collection(Constants.Key_Collection_Users)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    loading(false);
                    Log.d("l1 1111111", "_______________");
                    managePreferences.putBoolean(Constants.Key_Is_Signed_In, true);
                    Log.d("s1 1111111", "_______________");
                    managePreferences.putString(Constants.Key_Users_Id, documentReference.getId());
                    Log.d("s2 1111111", "_______________");
                    managePreferences.putString(Constants.Key_Name, name_in.getText().toString());
                    Log.d("s3 1111111", "_______________");
                    managePreferences.putString(Constants.Key_Image, image);
                    Log.d("s4 1111111", "_______________");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Log.d("sa 1111111", "_______________");
                })
                .addOnFailureListener(exception ->{
                    Log.d("e1 1111111", "_______________");
                    loading(false);
                    showToast(exception.getMessage());
                    Log.d("l2 1111111", "_______________");
                });

    }
    private String getImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes= byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if(result.getResultCode()==RESULT_OK){
                    if(result.getData() != null){
                        Uri imgUri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(imgUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imageProf.setImageBitmap(bitmap);
                            addImg.setVisibility(View.GONE);
                            image = getImage(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
    private void loading(boolean isLoading){
        Log.d("l3 1111111", "_______________");
        if(isLoading){
            signUp_btn.setVisibility(View.INVISIBLE);
            loadingProgressBar.setVisibility(View.VISIBLE);
            Log.d("l4 1111111", "_______________");
        }else{
            signUp_btn.setVisibility(View.VISIBLE);
            loadingProgressBar.setVisibility(View.INVISIBLE);
            Log.d("l5 1111111", "_______________");
        }

    }

    public void addingImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImage.launch(intent);
    }
    private void showToast(String mssg){
        Toast.makeText(this, mssg, Toast.LENGTH_SHORT).show();
    }

}