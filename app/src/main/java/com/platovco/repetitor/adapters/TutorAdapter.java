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
import com.platovco.repetitor.models.StudentAccount;
import com.platovco.repetitor.models.cardStudent;

import java.util.ArrayList;
import java.util.List;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorViewHolder> {

    private List<StudentAccount> tutorList;
    private OnItemClickListener listener;


    List<cardStudent> cardList = new ArrayList<>();
    private Context context; // Поле для хранения контекста

    // Конструктор для передачи списка данных и контекста
    public TutorAdapter(Context context, List<StudentAccount> tutorList, OnItemClickListener listener) {
        this.context = context;
        this.tutorList = tutorList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String tutorId);
    }

    public void setStudents(List<StudentAccount> students) {
        this.tutorList = students;
        notifyDataSetChanged();
    }

    // Создание ViewHolder, который будет привязывать данные к макету элемента списка
    public static class TutorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView cityTextView;
        TextView directionTextView;
        ImageView photoImageView;
        private List<StudentAccount> tutorList;

        public TutorViewHolder(View itemView, final OnItemClickListener listener, List<StudentAccount> tutorList)  {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            cityTextView = itemView.findViewById(R.id.tv_city);
            directionTextView = itemView.findViewById(R.id.tv_direction);
            photoImageView = itemView.findViewById(R.id.iv_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String tutorID = String.valueOf( tutorList.get(position).getId());
                            Log.d("Adapter Tutor", tutorList.get(position).getCity());
                            Log.d("Adapter Tutor", String.valueOf( tutorList.get(position).getId()));
                            listener.onItemClick(tutorID);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new TutorViewHolder(view, listener, tutorList);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorViewHolder holder, int position) {
        StudentAccount tutor = tutorList.get(position);
        holder.nameTextView.setText(tutor.getName());
        holder.cityTextView.setText(tutor.getCity());
        holder.directionTextView.setText(tutor.getDirection());
        Log.d("Adapter Tutor", String.valueOf( tutor.getPhoto()));
        Glide.with(context)
                .load(tutor.getPhoto())
                .into(holder.photoImageView);
    }

    @Override
    public int getItemCount() {
        // Возвращает количество элементов в списке
        return tutorList.size();
    }
}
