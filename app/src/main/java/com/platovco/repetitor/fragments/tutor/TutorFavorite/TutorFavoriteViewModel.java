package com.platovco.repetitor.fragments.tutor.TutorFavorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.room.AppDatabase;
import com.platovco.repetitor.room.StudentAccount;
import com.platovco.repetitor.room.StudentAccountDao;

import java.util.List;

public class TutorFavoriteViewModel extends AndroidViewModel {

    private final LiveData<List<StudentAccount>> favoriteTutors;

    public TutorFavoriteViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.Companion.getDatabase(application);
        StudentAccountDao studentAccountDao = database.studentAccountDao();
        favoriteTutors = studentAccountDao.getAllFavorites(); // Предполагается, что у вас есть такой метод в DAO
    }

    public LiveData<List<StudentAccount>> getFavoriteTutors() {
        return favoriteTutors;
    }
}