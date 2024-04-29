package com.platovco.repetitor.fragments.general.AddStudentInformation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.platovco.repetitor.R;
import com.platovco.repetitor.databinding.FragmentAddStudentInformationBinding;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.managers.CompressorManager;
import com.platovco.repetitor.models.StudentAccount;
import com.platovco.repetitor.utils.CustomTextWatcher;
import com.platovco.repetitor.utils.PhotoUtil;

import java.io.File;
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

public class AddStudentInformationFragment extends Fragment {

    private FragmentAddStudentInformationBinding binding;
    private ImageView ivAvatar;
    private EditText etName;
    private Button btnDone;
    private EditText etHigh;
    private AddStudentInformationViewModel mViewModel;
    private DatabaseReference mDatabase;

    public static AddStudentInformationFragment newInstance() {
        return new AddStudentInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddStudentInformationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddStudentInformationViewModel.class);
        init();
        observe();
        initListener();
    }
    private void init(){
        ivAvatar = binding.piAvatar;
        etName = binding.etName;
        btnDone = binding.btnDone;
        etHigh = binding.etCity;
    }

    private void observe() {
        mViewModel.ageLD.observe(getViewLifecycleOwner(), model ->
                binding.etCity.setText(model));
        mViewModel.nameLD.observe(getViewLifecycleOwner(), brand ->
                binding.etName.setText(brand));
        mViewModel.photoUri.observe(getViewLifecycleOwner(), uri ->
                Glide.with(requireContext())
                        .load(uri)
                        .into((ImageView) ivAvatar));
    }

    private void initListener() {
        binding.etName.addTextChangedListener( new CustomTextWatcher(mViewModel.nameLD));
        binding.etCity.addTextChangedListener( new CustomTextWatcher(mViewModel.ageLD));
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

    private void createDocument(){
        if (mViewModel.nameLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите Ваше имя", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mViewModel.nameLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите Ваш возраст", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mViewModel.photoUri.getValue() == null) {
            Toast.makeText(getContext(), "Прикрепите ваше фото", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        @SuppressLint("NotifyDataSetChanged") AddStudentInformationFragment.PhotosDownloadedCallback photosDownloadedCallback = () -> {
            StudentAccount studentAccount = mViewModel.createStudentAccount();
            AppwriteManager.INSTANCE.addStudentAccount(studentAccount, AppwriteManager.INSTANCE.getContinuation((s, throwable) -> {
                if (throwable != null) Log.e("add", String.valueOf(throwable));

            }));


            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() ->
                    Navigation.findNavController(requireActivity(), R.id.globalNavContainer).navigate(R.id.action_addStudentInformationFragment_to_studentMainFragment));
        };
        if (mViewModel.photoUri.getValue() == null) {
            photosDownloadedCallback.allPhotosDownloaded();
        } else {
            MutableLiveData<User<Map<String, Object>>> liveData = new MutableLiveData<>();
            AppwriteManager.INSTANCE.getAccount(liveData, AppwriteManager.INSTANCE.getContinuation((s, throwable) -> {
            }));
            liveData.observe(getViewLifecycleOwner(), objectAccount ->
                    sendPhoto(mViewModel.photoUri.getValue(), objectAccount.getId(), photosDownloadedCallback));
        }
    }

    public void sendPhoto(Uri uri, String uuid, AddStudentInformationFragment.PhotosDownloadedCallback photosSentCallback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->
                Observable.just(uri)
                        .flatMap(imageUri -> compressImage(new File(Objects.requireNonNull(PhotoUtil.getPathFromUri(requireContext(), imageUri)))))
                        .flatMap(compressedImage -> Objects.requireNonNull(uploadImage(compressedImage, uuid))
                                .subscribeOn(AndroidSchedulers.mainThread()))
                        .subscribe(
                                url -> {
                                    Log.d("Загружен URL: ", String.valueOf(url));
                                    mViewModel.photoUrl.setValue(url);
                                },
                                Throwable::printStackTrace,
                                () -> {
                                    Log.d("Загружены URL: ", "Все изображения были загружены");
                                    photosSentCallback.allPhotosDownloaded();
                                }
                        ));
    }

    private Observable<String> uploadImage(File file, String uuid) {
        try{
            AppwriteManager.INSTANCE.createFileStudentStorage(uuid, file, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
                Log.d("AppW Result: ", String.valueOf(result));
                Log.d("AppW Exception: ", String.valueOf(throwable));
            }));
            Callable<String> callable = () -> "http://89.253.219.76/v1/storage/buckets/65251e9c04cdd06bcec8/files/"+uuid+"/view?project=649d4dbdcf623484dd45";
            return Observable.fromCallable(callable);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Observable<File> compressImage(File file) {
        PublishProcessor<File> myDelayedObservable = PublishProcessor.create();
        CompressorManager.INSTANCE.compress(requireContext(), file, CompressorManager.INSTANCE.getContinuation((compressedFile, throwable) -> {
            myDelayedObservable.onNext(compressedFile);
            myDelayedObservable.onComplete();

        }));
        return Observable.fromPublisher(myDelayedObservable);
    }

    private interface PhotosDownloadedCallback {
        void allPhotosDownloaded();
    }
}