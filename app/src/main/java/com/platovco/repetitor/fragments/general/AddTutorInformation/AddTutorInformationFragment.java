package com.platovco.repetitor.fragments.general.AddTutorInformation;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.platovco.repetitor.R;
import com.platovco.repetitor.adapters.ChooseDirectionAdapter;
import com.platovco.repetitor.adapters.ChooseHighAdapter;
import com.platovco.repetitor.databinding.FragmentAddTutorInformationBinding;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.managers.CompressorManager;
import com.platovco.repetitor.models.TutorAccount;
import com.platovco.repetitor.utils.CustomTextWatcher;
import com.platovco.repetitor.utils.PhotoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.appwrite.models.User;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.processors.PublishProcessor;

public class AddTutorInformationFragment extends Fragment {

    private FragmentAddTutorInformationBinding binding;
    private ImageView ivAvatar;
    private EditText etName;
    private EditText etExperience;
    private Button btnDone;
    private EditText etHigh;
    private EditText etDirection;
    private RecyclerView rvHigh;
    private RecyclerView rvDirection;
    ArrayList<String> brands = new ArrayList<>();
    ArrayList<String> allBrands = new ArrayList<>();
    private ChooseHighAdapter adapterHigh;
    ArrayList<String> allDirections = new ArrayList<>();
    ArrayList<String> directions = new ArrayList<>();
    private ChooseDirectionAdapter adapterDirections;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private AddTutorInformationViewModel mViewModel;

    public static AddTutorInformationFragment newInstance() {
        return new AddTutorInformationFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTutorInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddTutorInformationViewModel.class);
        init();
        observe();
        initListener();
    }

    private void init(){
        ivAvatar = (ImageView) binding.piAvatar;
        etName = binding.etName;
        etExperience = binding.etExperience;
        btnDone = binding.btnDone;
        etHigh = binding.etHigh;
        etDirection = binding.etDirection;
    }

    private void initListener(){
        etName.addTextChangedListener( new CustomTextWatcher(mViewModel.nameLD));
        etExperience.addTextChangedListener( new CustomTextWatcher(mViewModel.experienceLD));
        etHigh.addTextChangedListener( new CustomTextWatcher(mViewModel.highLD));
        etDirection.addTextChangedListener( new CustomTextWatcher(mViewModel.directionLD));

        ivAvatar.setOnClickListener(view -> TedImagePicker.with(requireContext())
                .start(uri -> {
                    mViewModel.photoUri.setValue(uri);
                    Glide.with(requireContext())
                            .load(uri)
                            .into((ImageView) view);
                    binding.avatarTV.setText("Сменить фото");
                }));
        btnDone.setOnClickListener(view -> createDocument());
    }

    private void observe () {
        mViewModel.directionLD.observe(getViewLifecycleOwner(), model ->
                etDirection.setText(model));
        mViewModel.highLD.observe(getViewLifecycleOwner(), brand ->
                etHigh.setText(brand));
        mViewModel.photoUri.observe(getViewLifecycleOwner(), uri ->
                Glide.with(requireContext())
                        .load(uri)
                        .into((ImageView) ivAvatar));
    }

    private void createDocument(){
        if (mViewModel.nameLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите ваше ФИО", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.photoUri.getValue() == null) {
            Toast.makeText(getContext(), "Прикрепите ваше фото", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.highLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите ВУЗ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.directionLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите направление", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.experienceLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите стаж", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://mentorium-9e9e2-default-rtdb.europe-west1.firebasedatabase.app").getReference( );
        TutorAccount user = new TutorAccount(mViewModel.photoUrl.toString(),
                mViewModel.nameLD.getValue().toString(),
                mViewModel.highLD.getValue().toString(),
                mViewModel.directionLD.getValue().toString(),
                mViewModel.experienceLD.getValue().toString(),
                mAuth.getUid());
        mDatabase.child("users").child(mAuth.getUid()).setValue(user);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() ->
                Navigation.findNavController(requireActivity(), R.id.globalNavContainer).navigate(R.id.action_addTutorInformationFragment_to_tutorMainFragment));
    }

    public Observable<File> compressImage(File file) {
        PublishProcessor<File> myDelayedObservable = PublishProcessor.create();
        CompressorManager.INSTANCE.compress(requireContext(), file, CompressorManager.INSTANCE.getContinuation((compressedFile, throwable) -> {
            myDelayedObservable.onNext(compressedFile);
            myDelayedObservable.onComplete();
        }));
        return Observable.fromPublisher(myDelayedObservable);
    }
}