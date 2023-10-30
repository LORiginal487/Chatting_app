package com.example.chatting_app.java_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chatting_app.Listeners.UserListener;
import com.example.chatting_app.R;
import com.example.chatting_app.adapters.UsersAdapter;
import com.example.chatting_app.models.User;
import com.example.chatting_app.utilities.Constants;
import com.example.chatting_app.utilities.ManagePreferences;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class usersActivity extends AppCompatActivity implements UserListener {
    TextView error;
    private ManagePreferences managePreferences;
    RecyclerView recyclerView;
    ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        //________________-*
        managePreferences = new ManagePreferences(getApplicationContext());
        callViews();
        getUsers();

        //________________-*
    }

    public void Go_Back(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void getUsers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.Key_Collection_Users)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String current_USERiD = managePreferences.getString(Constants.Key_Users_Id);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (current_USERiD.equals(queryDocumentSnapshot.getId())) {
                                continue;
                            }
                            User user = new User();
                            user.name = queryDocumentSnapshot.getString(Constants.Key_Name);
                            user.email = queryDocumentSnapshot.getString(Constants.Key_Email);
                            user.image = queryDocumentSnapshot.getString(Constants.Key_Image);
                            user.token = queryDocumentSnapshot.getString(Constants.Key_FCM_Token);
                            user.id = queryDocumentSnapshot.getId();
                            users.add(user);
                        }
                        if (users.size() > 0) {
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            recyclerView.setAdapter(usersAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorTxt();
                        }
                    } else {
                        showErrorTxt();
                    }
                });
    }

    public void showErrorTxt() {
        error.setText(String.format("%s", "No User Found"));
        error.setVisibility(View.VISIBLE);
    }

    private void loading(boolean isLoading) {
        //Log.d("l3 1111111", "_______________");
        if (isLoading) {

            loadingProgressBar.setVisibility(View.VISIBLE);
            //Log.d("l4 1111111", "_______________");
        } else {

            loadingProgressBar.setVisibility(View.INVISIBLE);
            //Log.d("l5 1111111", "_______________");
        }

    }

    private void callViews() {
        loadingProgressBar = findViewById(R.id.progressBar3);
        error = findViewById(R.id.txtError);
        recyclerView = findViewById(R.id.recycleV1);
    }

    @Override
    public void onUserClick(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.Key_User, user);
        startActivity(intent);
        finish();
    }
}