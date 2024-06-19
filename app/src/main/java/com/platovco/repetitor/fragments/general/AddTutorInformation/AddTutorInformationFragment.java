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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private EditText etSurname;
    private EditText etCity;
    private EditText etBirthday;
    private EditText etExperience;
    private Button btnDone;
    private EditText etHigh;
    private EditText etDirection;
    private EditText etPrice;
    private EditText etReason;
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

    private static final String TAG = "MainActivity";

    //@Override
    //public void onCreate(@Nullable Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
//
    //    mDatabase = FirebaseDatabase.getInstance("https://mentorium-9e9e2-default-rtdb.europe-west1.firebasedatabase.app").getReference( );
//
    //    try {
    //        InputStream inputStream = getContext().getAssets().open("students.csv");
    //        BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));
    //        String line;
    //        int i = 0;
    //        while ((line = csvReader.readLine()) != null) {
    //            Log.d("TEst", "Запись начинается");
    //            String[] tokens = line.split(";");
    //            if (tokens.length >= 19) {
//
    //                TutorAccount user = new TutorAccount("https://cdnn21.img.ria.ru/images/07e4/03/19/1569127302_0:210:2000:1335_600x0_80_0_0_e94286a549948e7d750da4574bfadf76.jpg",
    //                        tokens[0],
    //                        tokens[0],
    //                        tokens[4],
    //                        "1984-01-01",
    //                        tokens[10] + ", " + tokens[11] + ", " + tokens[12],
    //                        tokens[14],
    //                        tokens[15],
    //                        tokens[16],
    //                        Integer.toString(i));
    //                mDatabase.child("students").child(Integer.toString(i)).setValue(user);
    //                Log.d("TEst", tokens[0]);
    //                Log.d("TEst", tokens[4]);
    //                Log.d("TEst", tokens[10] + ", " + tokens[11] + ", " + tokens[12]);
    //                Log.d("TEst", tokens[14]);
    //                Log.d("TEst", tokens[15]);
    //                Log.d("TEst", tokens[16]);
    //                Log.d("TEst", Integer.toString(i));
//
    //                Log.d("TEst", "Запись прошла"+i);
    //                Log.d("TEst", user.getCity());
    //                i++;
    //            }
    //            Log.d("TEst", "Запись закончена");
    //            Thread.sleep(10L);
    //        }
    //        csvReader.close();
    //    } catch (IOException e) {
    //        Log.e(TAG, "Error reading CSV file", e);
    //    } catch (InterruptedException e) {
    //        throw new RuntimeException(e);
    //    }
    //}


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
        etSurname = binding.etSurname;
        etCity = binding.etCity;
        etBirthday = binding.etBirthday;
        etReason = binding.etReason;
        btnDone = binding.btnDone;
        etHigh = binding.etHigh;
        etDirection = binding.etDirection;
        etPrice = binding.etPrice;
    }

    private void initListener(){
        etName.addTextChangedListener( new CustomTextWatcher(mViewModel.nameLD));
        etSurname.addTextChangedListener( new CustomTextWatcher(mViewModel.surnameLD));
        etCity.addTextChangedListener( new CustomTextWatcher(mViewModel.cityLD));
        etBirthday.addTextChangedListener( new CustomTextWatcher(mViewModel.birthdayLD));
        etHigh.addTextChangedListener( new CustomTextWatcher(mViewModel.highLD));
        etDirection.addTextChangedListener( new CustomTextWatcher(mViewModel.directionLD));
        etPrice.addTextChangedListener( new CustomTextWatcher(mViewModel.priceLD));
        etReason.addTextChangedListener( new CustomTextWatcher(mViewModel.reasonLD));

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

        mViewModel.photoUri.observe(getViewLifecycleOwner(), uri ->
                Glide.with(requireContext())
                        .load(uri)
                        .into((ImageView) ivAvatar));
    }

    private void createDocument(){
        if (mViewModel.nameLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите ваше имя", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mViewModel.surnameLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите вашу фамилию", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.photoUri.getValue() == null) {
            Toast.makeText(getContext(), "Прикрепите ваше фото", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.cityLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите ваш город", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.birthdayLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите дату рождения", Toast.LENGTH_SHORT).show();
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
        if (mViewModel.priceLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите стоимость услуг", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.reasonLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите цель подготовки", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://mentorium-9e9e2-default-rtdb.europe-west1.firebasedatabase.app").getReference( );
        TutorAccount user = new TutorAccount(mViewModel.photoUrl.toString(),
                mViewModel.nameLD.getValue().toString(),
                mViewModel.surnameLD.getValue().toString(),
                mViewModel.cityLD.getValue().toString(),
                mViewModel.birthdayLD.getValue().toString(),
                mViewModel.highLD.getValue().toString(),
                mViewModel.directionLD.getValue().toString(),
                mViewModel.priceLD.getValue().toString(),
                mViewModel.reasonLD.getValue().toString(),
                mAuth.getUid());
        mDatabase.child("tutors").child(mAuth.getUid()).setValue(user);
//
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