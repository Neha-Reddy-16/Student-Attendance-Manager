package com.example.attendence.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.attendence.databinding.ActivityAddEditSubjectBinding
import com.example.attendence.model.Subject
import com.example.attendence.viewmodel.SubjectViewModel

class AddEditSubjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditSubjectBinding
    private val subjectViewModel: SubjectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val name = binding.subjectNameEditText.text.toString().trim()
            if (name.isNotEmpty()) {
                val subject = Subject(name = name, totalClasses = 0, attendedClasses = 0)
                subjectViewModel.insert(subject)
                finish()
            } else {
                Toast.makeText(this, "Please enter a subject name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
