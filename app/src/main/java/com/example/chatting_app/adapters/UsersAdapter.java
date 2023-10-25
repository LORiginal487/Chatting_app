package com.example.chatting_app.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatting_app.R;
import com.example.chatting_app.models.User;
import com.example.chatting_app.utilities.Constants;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private final List<User> users;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details_container, parent, false);
        return new UserViewHolder(view);

    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        // Bind data to the views in the ViewHolder
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the data set
        return users.size(); // Change this to the actual item count
    }

    private Bitmap getUserImage(String encodedImg){
        byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
    }
    class UserViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        public UserViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
        void setUserData(User user){
            TextView name = itemView.findViewById(R.id.textName);
            name.setText(user.name);
            TextView email = itemView.findViewById(R.id.textEmail);
            email.setText(user.email);
            RoundedImageView imageProfile = itemView.findViewById(R.id.imageV1);
            imageProfile.setImageBitmap(getUserImage(user.image));
        }
    }
}
