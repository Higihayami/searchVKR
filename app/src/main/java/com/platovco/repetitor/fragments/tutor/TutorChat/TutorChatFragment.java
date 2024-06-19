package com.platovco.repetitor.fragments.tutor.TutorChat;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.platovco.repetitor.R;
import com.platovco.repetitor.adapters.ChatAdapter;
import com.platovco.repetitor.databinding.FragmentTutorChatBinding;
import com.platovco.repetitor.databinding.FragmentTutorSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class TutorChatFragment extends Fragment {

    private FragmentTutorChatBinding binding;
    private RecyclerView chatListRecyclerView;
    private ChatAdapter chatAdapter;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTutorChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAG", "chats.get(0).getMessages().get(0).getText()");

        chatListRecyclerView = binding.chatListRecyclerView;
        chatAdapter = new ChatAdapter(new ArrayList<>());
        chatListRecyclerView.setAdapter(chatAdapter);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mDatabase = FirebaseDatabase.getInstance("https://mentorium-9e9e2-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("chats");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Chat> chats = new ArrayList<>();
                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = chatSnapshot.getValue(Chat.class);
                    chats.add(chat);
                }
                Log.d("TAG", chats.get(0).getMessages().get(0).getText());
                // Update RecyclerView with the chats data using the adapter
                chatAdapter.setChats(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Failed to read chats.", databaseError.toException());
            }
        });
    }

    // Other fragment lifecycle methods
}