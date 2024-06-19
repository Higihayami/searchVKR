package com.platovco.repetitor.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.platovco.repetitor.R;
import com.platovco.repetitor.fragments.tutor.TutorChat.Chat;
import com.platovco.repetitor.models.StudentAccount;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Chat> chatList;

    // Constructor, setter for chatList, and other necessary methods

    public  ChatAdapter(List<Chat> chatList){
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);

        holder.userNameTextView.setText("Студент");
        holder.lastMessageTextView.setText(chat.getMessages().get(0).getText());

        // Set user name, last message, etc. based on chat data
        // Use Firebase queries to fetch user information and last message
    }

    public void setChats(List<Chat> chats) {
        this.chatList = chats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTextView;
        private TextView lastMessageTextView;

        public ChatViewHolder(View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.chatUserName);
            lastMessageTextView = itemView.findViewById(R.id.chatLastMessage);
        }
    }
}
