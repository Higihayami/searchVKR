package com.platovco.repetitor.fragments.tutor.TutorSearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.StudentAccount;
import com.platovco.repetitor.models.cardStudent;

import java.util.ArrayList;


public class TutorSearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<StudentAccount>> studentsLD = new MutableLiveData<>();

    public LiveData<ArrayList<StudentAccount>> getStudentsLD() {
        return studentsLD;
    }

    public void updateStudentsList(ArrayList<StudentAccount> students) {
        studentsLD.setValue(students);
    }
}