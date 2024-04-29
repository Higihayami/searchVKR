package com.platovco.repetitor.fragments.tutor.TutorSearch;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platovco.repetitor.R;
import com.platovco.repetitor.adapters.CardAdapterTutor;
import com.platovco.repetitor.adapters.TutorAdapter;
import com.platovco.repetitor.adapters.TutorData;
import com.platovco.repetitor.databinding.FragmentTutorSearchBinding;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.models.cardStudent;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class TutorSearchFragment extends Fragment {

    private FragmentTutorSearchBinding binding;
    private TutorSearchViewModel mViewModel;
    private RecyclerView cardStack;
    ArrayList<cardStudent> allStudents = new ArrayList<>();
    ArrayList<cardStudent> students = new ArrayList<>();

    public static TutorSearchFragment newInstance() {
        return new TutorSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTutorSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<TutorData> fakeTutorList = generateFakeData(10);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        TutorAdapter adapter = new TutorAdapter(getContext(), fakeTutorList);
        recyclerView.setAdapter(adapter);
    }

    private List<TutorData> generateFakeData(int count) {
        List<TutorData> DataList = new ArrayList<>();
        String photoUrl = "https://www.profguide.io/images/article/a/51/W8aCmKa3LE.webp";
        String name = "Иван Петров";
        String city = "Казань";
        String direction = "Математика";
        TutorData tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://api.synergytimes.ru/upload/iblock/cc3/3q0p23sxn6lxnpwltscog0cikx3gcvp6.jpeg";
        name = "Андрей Козлов";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://static.tildacdn.com/tild6636-6566-4764-a138-623339633138/1_2_1.jpg";
        name = "Наталья Васильева";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQk114m3sxmPRfDioAquhvgzr85_jEyvFsKdw0cIhV5Q&s";
        name = "Никита Соколов";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://xn--80adrabb4aegksdjbafk0u.xn--p1ai/upload/iblock/509/zohvqzenra0223bcxtv3jy99mpfb3jqp.jpg";
        name = "София Кузнецова";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://api.synergytimes.ru/upload/iblock/cc3/3q0p23sxn6lxnpwltscog0cikx3gcvp6.jpeg";
        name = "Елена Николаева";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://st3.depositphotos.com/2492691/13346/i/450/depositphotos_133466140-stock-photo-business-man-working.jpg";
        name = "Константин Иванов";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://gazon.media/userfls/news/large/2/10346_chto-nuzhno-uspet-poka-ty-st.jpg";
        name = "Ольга Петрова";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://goo.gl/gEgYUd";
        name = "Никита Соколов";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        photoUrl = "https://cdnn21.img.ria.ru/images/07e4/03/19/1569127302_0:210:2000:1335_600x0_80_0_0_e94286a549948e7d750da4574bfadf76.jpg";
        name = "Павел Морозов";
        city = "Казань";
        direction = "Математика";
        tutorData = new TutorData(name, city, direction, photoUrl);
        DataList.add(tutorData);
        return DataList;
    }

    private void initListener(){
        mViewModel.studentsLD.observe(getViewLifecycleOwner(), s  ->{
            allStudents.clear();
            allStudents.addAll(mViewModel.studentsLD.getValue());
            students = new ArrayList<cardStudent>(allStudents);
            CardAdapterTutor adapter = new CardAdapterTutor(students, this.getActivity());
        });
    }

    private void observe(){}

}