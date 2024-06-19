package com.platovco.repetitor.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StudentAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(studentAccount: StudentAccount)

    @Query("SELECT * FROM student_accounts WHERE id = :id")
    suspend fun getStudentAccount(id: Long): StudentAccount?

    @Query("SELECT * FROM student_accounts")
    fun getAllFavorites(): LiveData<List<StudentAccount?>?>?
}
