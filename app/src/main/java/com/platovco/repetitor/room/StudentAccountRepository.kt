package com.platovco.repetitor.room

class StudentAccountRepository(private val studentAccountDao: StudentAccountDao) {
    suspend fun insert(studentAccount: StudentAccount) {
        studentAccountDao.insert(studentAccount)
    }

    suspend fun getStudentAccount(id: Long): StudentAccount? {
        return studentAccountDao.getStudentAccount(id)
    }
}
