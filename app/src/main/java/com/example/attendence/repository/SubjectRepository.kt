package com.example.attendence.repository

import androidx.lifecycle.LiveData
import com.example.attendence.data.SubjectDao
import com.example.attendence.model.Subject

class SubjectRepository(private val dao: SubjectDao) {
    val allSubjects: LiveData<List<Subject>> = dao.getAllSubjects()

    suspend fun insert(subject: Subject) = dao.insert(subject)
    suspend fun update(subject: Subject) = dao.update(subject)
    suspend fun delete(subject: Subject) = dao.delete(subject)
}