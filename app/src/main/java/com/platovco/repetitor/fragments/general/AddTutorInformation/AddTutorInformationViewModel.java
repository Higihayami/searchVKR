package com.platovco.repetitor.fragments.general.AddTutorInformation;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.TutorAccount;

import java.util.ArrayList;

public class AddTutorInformationViewModel extends ViewModel {

    public MutableLiveData<String> photoUrl = new MutableLiveData<>();
    public MutableLiveData<Uri> photoUri = new MutableLiveData<>();
    public MutableLiveData<String> experienceLD = new MutableLiveData<>();
    public MutableLiveData<String> nameLD = new MutableLiveData<>();
    public MutableLiveData<String> surnameLD = new MutableLiveData<>();

    public MutableLiveData<String> cityLD = new MutableLiveData<>();
    public MutableLiveData<String> birthdayLD = new MutableLiveData<>();
    public MutableLiveData<String> highLD = new MutableLiveData<>();
    public MutableLiveData<String> directionLD = new MutableLiveData<>();
    public MutableLiveData<String> priceLD = new MutableLiveData<>();
    public MutableLiveData<String> reasonLD = new MutableLiveData<>();
    public MutableLiveData<String> idLD = new MutableLiveData<>();
    MutableLiveData<ArrayList<String>> highsLD = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<ArrayList<String>> directionsLD = new MutableLiveData<>(new ArrayList<>());


    public TutorAccount createTutorAccount(){
        return new TutorAccount(photoUrl.getValue(), nameLD.getValue(), surnameLD.getValue(), cityLD.getValue(),
                birthdayLD.getValue(), highLD.getValue(), directionLD.getValue(), priceLD.getValue(), reasonLD.getValue(), idLD.getValue() );
    }
}