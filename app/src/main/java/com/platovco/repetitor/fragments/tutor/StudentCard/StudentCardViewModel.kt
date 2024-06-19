package com.platovco.repetitor.fragments.tutor.StudentCard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.platovco.repetitor.models.StudentAccount
import com.platovco.repetitor.room.AppDatabase
import com.platovco.repetitor.room.StudentAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentCardViewModel(application: Application) : AndroidViewModel(application) {
    private val _studentAccount = MutableLiveData<StudentAccount>()
    val studentAccount: LiveData<StudentAccount> get() = _studentAccount

    private val repository: StudentAccountRepository

    init {
        val studentAccountDao = AppDatabase.getDatabase(application).studentAccountDao()
        repository = StudentAccountRepository(studentAccountDao)
    }

    fun saveStudentAccount(studentAccount: com.platovco.repetitor.room.StudentAccount) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(studentAccount)
        }
    }
    fun setStudentAccount(studentAccount: StudentAccount) {
        _studentAccount.value = studentAccount
    }
}