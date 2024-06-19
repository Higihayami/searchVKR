package com.platovco.repetitor.fragments.tutor.TutorFavorite;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platovco.repetitor.R;
import com.platovco.repetitor.adapters.TutorAdapter;
import com.platovco.repetitor.models.StudentAccount;

import java.util.ArrayList;

public class TutorFavoriteFragment extends Fragment {

    private TutorFavoriteViewModel mViewModel;
    private TutorAdapter tutorAdapter;
    private RecyclerView favoritesRecyclerView;

    public static TutorFavoriteFragment newInstance() {
        return new TutorFavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutor_favorite, container, false);

        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tutorAdapter = new TutorAdapter(getContext(), new ArrayList<>(), tutorId -> {
            // Здесь можно обработать нажатие на элемент
        });
        favoritesRecyclerView.setAdapter(tutorAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(TutorFavoriteViewModel.class);

        mViewModel.getFavoriteTutors().observe(getViewLifecycleOwner(), tutors -> {
            ArrayList<StudentAccount> students = new ArrayList<>();
            for(int i = 0; i< tutors.size(); i++){
                StudentAccount student = new StudentAccount(
                        tutors.get(i).getPhoto(),
                        tutors.get(i).getName(),
                        tutors.get(i).getSurname(),
                        tutors.get(i).getCity(),
                        tutors.get(i).getBirthday(),
                        tutors.get(i).getDirection(),
                        tutors.get(i).getBudget(),
                        tutors.get(i).getReason(),
                        tutors.get(i).getId()
                );
                students.add(student);
            }



            tutorAdapter.setStudents(students);
        });
    }
}