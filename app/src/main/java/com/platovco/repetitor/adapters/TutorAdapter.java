package com.platovco.repetitor.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.platovco.repetitor.R;

import java.util.List;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorViewHolder> {

    private List<TutorData> tutorList;
    private Context context; // Поле для хранения контекста

    // Конструктор для передачи списка данных и контекста
    public TutorAdapter(Context context, List<TutorData> tutorList) {
        this.context = context;
        this.tutorList = tutorList;
    }

    // Создание ViewHolder, который будет привязывать данные к макету элемента списка
    public static class TutorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView cityTextView;
        TextView directionTextView;
        ImageView photoImageView;

        public TutorViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            cityTextView = itemView.findViewById(R.id.tv_city);
            directionTextView = itemView.findViewById(R.id.tv_direction);
            photoImageView = itemView.findViewById(R.id.iv_photo);
        }
    }

    @NonNull
    @Override
    public TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new TutorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorViewHolder holder, int position) {
        TutorData tutor = tutorList.get(position);
        holder.nameTextView.setText(tutor.getName());
        holder.cityTextView.setText(tutor.getCity());
        holder.directionTextView.setText(tutor.getDirection());
        Log.d("Glide", tutor.getPhotoUrl());

        Glide.with(context)
                .load(tutor.getPhotoUrl())
                .into(holder.photoImageView);
    }

    @Override
    public int getItemCount() {
        // Возвращает количество элементов в списке
        return tutorList.size();
    }
}
