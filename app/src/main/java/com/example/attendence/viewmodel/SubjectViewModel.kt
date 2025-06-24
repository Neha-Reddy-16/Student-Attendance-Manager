package com.example.attendence.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.attendence.data.SubjectDatabase
import com.example.attendence.model.Subject
import com.example.attendence.repository.SubjectRepository
import kotlinx.coroutines.launch

class SubjectViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SubjectRepository
    val allSubjects: LiveData<List<Subject>>

    init {
        val dao = SubjectDatabase.getDatabase(application).subjectDao()
        repository = SubjectRepository(dao)
        allSubjects = repository.allSubjects
    }

    fun insert(subject: Subject) = viewModelScope.launch { repository.insert(subject) }
    fun update(subject: Subject) = viewModelScope.launch { repository.update(subject) }
    fun delete(subject: Subject) = viewModelScope.launch { repository.delete(subject) }
}