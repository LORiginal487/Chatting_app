package com.example.chatting_app.adapters;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatting_app.R;
import com.example.chatting_app.models.ChatMessage;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ChatMessage> chatMessages;
    private static Bitmap receiverPP;
    String senderID;

    public static final int view_TYPE_SENT = 1;
    public static final int view_TYPE_RECEIVED = 2;


    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receiverPP, String senderID) {
        this.chatMessages = chatMessages;
        ChatAdapter.receiverPP = receiverPP;
        this.senderID = senderID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == view_TYPE_SENT) {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sent_message, parent, false);
            return new sentMessageHolder(view1);
        } else {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_received_message, parent, false);
            return new receivedMessageHolder(view1);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == view_TYPE_SENT) {
            ((sentMessageHolder) holder).setdata(chatMessages.get(position));
        } else {
            ((receivedMessageHolder) holder).setdata(chatMessages.get(position), receiverPP);
        }
    }

    public int getItemViewType(int position) {
        if (chatMessages.get(position).senderID.equals(senderID)) {
            return view_TYPE_SENT;
        } else {
            return view_TYPE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    static class sentMessageHolder extends RecyclerView.ViewHolder {
        View itemView;

        public sentMessageHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void setdata(ChatMessage chatMessage) {

            TextView message = itemView.findViewById(R.id.outgoing);
            message.setText(chatMessage.message);
            TextView dateTime = itemView.findViewById(R.id.sentTimeTxt);
            dateTime.setText(chatMessage.dateTime);


        }
    }

    static class receivedMessageHolder extends RecyclerView.ViewHolder {
        View itemView;

        public receivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void setdata(ChatMessage chatMessage, Bitmap receiverPP) {

            TextView message = itemView.findViewById(R.id.incoming);
            message.setText(chatMessage.message);
            TextView dateTime = itemView.findViewById(R.id.recTimeTxt);
            dateTime.setText(chatMessage.dateTime);
            RoundedImageView imageProfile = itemView.findViewById(R.id.senderimgProfile);
            imageProfile.setImageBitmap(ChatAdapter.receiverPP);


        }
    }
}
