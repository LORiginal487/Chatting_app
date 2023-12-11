package com.example.chatting_app.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatting_app.R;
import com.example.chatting_app.models.ChatMessage;
import com.example.chatting_app.models.User;
import com.makeramen.roundedimageview.RoundedImageView;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    private Bitmap getUserImage(String encodedImg) {
        byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
     class RecentUsersHolder extends RecyclerView.ViewHolder {
         View itemView;

         public RecentUsersHolder(View itemView) {
             super(itemView);
             this.itemView = itemView;
         }

         void setUserData(User user) {
             TextView name = itemView.findViewById(R.id.textName);
             name.setText(user.name);
             TextView lastText = itemView.findViewById(R.id.textEmail);
             lastText.setText(user.email);
             RoundedImageView imageProfile = itemView.findViewById(R.id.imageV1);
             imageProfile.setImageBitmap(getUserImage(user.image));
         }
    }
}
