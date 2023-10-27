package com.example.chatting_app.adapters;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatting_app.R;
import com.example.chatting_app.models.ChatMessage;
import com.example.chatting_app.models.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatMessage> chatMessages;
    private static final Bitmap receiverPP;
    String senderID;

    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receiverPP, String senderID) {
        this.chatMessages = chatMessages;
        this.receiverPP = receiverPP;
        this.senderID = senderID;
    }

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

    static class sentMessageHolder extends RecyclerView.ViewHolder{
        View itemView;
        public sentMessageHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
        void setdata(ChatMessage chatMessage){

            TextView message = itemView.findViewById(R.id.outgoing);
            message.setText(chatMessage.message);
            TextView dateTime = itemView.findViewById(R.id.sentTimeTxt);
            dateTime.setText(chatMessage.dateTime);


        }
    }
    static class receivedMessageHolder extends RecyclerView.ViewHolder{
        View itemView;
        public receivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
        void setdata(ChatMessage chatMessage){

            TextView message = itemView.findViewById(R.id.incoming);
            message.setText(chatMessage.message);
            TextView dateTime = itemView.findViewById(R.id.recTimeTxt);
            dateTime.setText(chatMessage.dateTime);
            RoundedImageView imageProfile = itemView.findViewById(R.id.senderimgProfile);
            imageProfile.setImageBitmap(receiverPP);


        }
    }
}
