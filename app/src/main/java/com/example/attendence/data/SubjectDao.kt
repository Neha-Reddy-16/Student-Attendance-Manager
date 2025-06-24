package com.example.attendence.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.attendence.model.Subject

@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: Subject)

    @Update
    suspend fun update(subject: Subject)

    @Delete
    suspend fun delete(subject: Subject)

    @Query("SELECT * FROM subject_table")
    fun getAllSubjects(): LiveData<List<Subject>>
}