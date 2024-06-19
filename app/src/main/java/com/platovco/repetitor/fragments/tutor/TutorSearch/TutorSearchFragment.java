package com.platovco.repetitor.fragments.tutor.TutorSearch;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.platovco.repetitor.R;
import com.platovco.repetitor.adapters.TutorAdapter;
import com.platovco.repetitor.databinding.FragmentTutorSearchBinding;
import com.platovco.repetitor.models.StudentAccount;

import java.util.ArrayList;

public class TutorSearchFragment extends Fragment {

    private FragmentTutorSearchBinding binding;
    private TutorSearchViewModel mViewModel;
    private RecyclerView recyclerView;
    private TutorAdapter adapter;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ValueEventListener dataListener;
    private NavController navController;

    public static TutorSearchFragment newInstance() {
        return new TutorSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTutorSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(TutorSearchViewModel.class);

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        navController = NavHostFragment.findNavController(this);

        adapter = new TutorAdapter(getActivity(), new ArrayList<>(), new TutorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String tutorId) {
                Bundle bundle = new Bundle();
                bundle.putString("tutorID", tutorId);
                navController.navigate(R.id.action_tutorSearchFragment_to_studentCardFragment, bundle);

            }
        });
        recyclerView.setAdapter(adapter);

        // Observe LiveData from ViewModel
        mViewModel.getStudentsLD().observe(getViewLifecycleOwner(), new Observer<ArrayList<StudentAccount>>() {
            @Override
            public void onChanged(ArrayList<StudentAccount> students) {
                // Update adapter with new data when LiveData changes
                adapter.setStudents(students);
                adapter.notifyDataSetChanged(); // Optional if adapter handles data set changes internally
            }
        });

        // Fetch data from Firebase
        fetchDataFromFirebase();
    }

    public interface fragment1NextClick {
        void  onFragment1NextClick();
    }

    private void fetchDataFromFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://mentorium-9e9e2-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        mDatabase.child("students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<StudentAccount> cardStudents = new ArrayList<>();

                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    StudentAccount student = studentSnapshot.getValue(StudentAccount.class);
                    if (student != null) {
                        Log.d("TAG", "Student Name: " + student.getName());

                        StudentAccount user = new StudentAccount(
                                student.getPhoto(),
                                student.getName(),
                                student.getSurname(),
                                student.getCity(),
                                student.getBirthday(),
                                student.getDirection(),
                                student.getBudget(),
                                student.getReason(),
                                student.getId()
                        );
                        cardStudents.add(user);
                    }
                }

                // Update ViewModel with the fetched data
                mViewModel.updateStudentsList(cardStudents);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up listeners if needed
    }
}