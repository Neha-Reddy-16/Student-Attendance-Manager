package com.example.attendence.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendence.databinding.ActivityMainBinding
import com.example.attendence.viewmodel.SubjectViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val subjectViewModel: SubjectViewModel by viewModels()
    private lateinit var adapter: SubjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SubjectAdapter(subjectViewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        subjectViewModel.allSubjects.observe(this) { subjects ->
            adapter.setSubjects(subjects)
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditSubjectActivity::class.java))
        }
    }
}