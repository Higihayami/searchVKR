package com.platovco.repetitor.fragments.tutor.TutorProfile;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.platovco.repetitor.databinding.FragmentTutorProfileBinding;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.managers.CompressorManager;
import com.platovco.repetitor.models.TutorAccount;
import com.platovco.repetitor.utils.PhotoUtil;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.Callable;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.processors.PublishProcessor;

public class TutorProfileFragment extends Fragment {

    private TutorProfileViewModel mViewModel;
    private FragmentTutorProfileBinding binding;

    TextView nameTV;
    ImageView userPhotoIV;
    TextView educationTV;
    TextView directionTV;
    TextView experienceTV;
    //ImageView editBTN;

    public static TutorProfileFragment newInstance() {
        return new TutorProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTutorProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TutorProfileViewModel.class);
    }
}