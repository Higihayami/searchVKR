package com.platovco.repetitor.fragments.tutor.StudentCard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.platovco.repetitor.R
import com.platovco.repetitor.databinding.FragmentStudentCardBinding
import com.platovco.repetitor.models.StudentAccount

class StudentCardFragment : Fragment() {

    companion object {
        fun newInstance() = StudentCardFragment()
    }

    private val viewModel: StudentCardViewModel by viewModels()

    private var _binding: FragmentStudentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tutorID = arguments?.getString("tutorID")
        Log.d("Tutor", tutorID.toString())
        fetchDataFromFirebase(tutorID)

        viewModel.studentAccount.observe(viewLifecycleOwner, { studentAccount ->
            studentAccount?.let {
                // Update your UI with the studentAccount data here
                initView()
            }
        })

        binding.btnFavorite.setOnClickListener {
            val studentAccount = viewModel.studentAccount.value
            val studentEntity  = com.platovco.repetitor.room.StudentAccount(
                id = studentAccount?.id ?:0,
                photo = studentAccount?.photo?:"",
                name = studentAccount?.name?:"",
                surname = studentAccount?.surname?:"",
                city = studentAccount?.city?:"",
                birthday = studentAccount?.birthday?:"",
                direction = studentAccount?.direction?:"",
                budget = studentAccount?.budget?:0,
                reason =studentAccount?.reason?:""
            )
            viewModel.saveStudentAccount(studentEntity)
            Toast.makeText(context, "Student account saved to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView(){
        binding.tv2.text = viewModel.studentAccount.value?.city
        binding.tvName.text = viewModel.studentAccount.value?.name
        binding.tv8.text = viewModel.studentAccount.value?.direction
        binding.tv6.text = viewModel.studentAccount.value?.reason
        binding.tvAges.text = viewModel.studentAccount.value?.birthday
    }

    private fun fetchDataFromFirebase(tutorId: String?) {
        val mDatabase = FirebaseDatabase.getInstance("https://mentorium-9e9e2-default-rtdb.europe-west1.firebasedatabase.app").reference

        mDatabase.child("students").child(tutorId.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue<StudentAccount>()
                value?.let {
                    viewModel.setStudentAccount(it)
                    Log.d("FireBase", it.city)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}