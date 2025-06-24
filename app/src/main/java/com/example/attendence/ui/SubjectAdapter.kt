package com.example.attendence.ui

import android.app.AlertDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.attendence.R
import com.example.attendence.model.Subject
import com.example.attendence.util.AttendanceUtils
import com.example.attendence.viewmodel.SubjectViewModel

class SubjectAdapter(
    private val viewModel: SubjectViewModel
) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    private var subjects: List<Subject> = listOf()

    fun setSubjects(subjects: List<Subject>) {
        this.subjects = subjects
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjects[position]
        holder.bind(subject)
    }

    override fun getItemCount(): Int = subjects.size

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.subjectNameText)
        private val percentText: TextView = itemView.findViewById(R.id.attendancePercentText)
        private val totalText: TextView = itemView.findViewById(R.id.totalClassesText)
        private val presentButton: Button = itemView.findViewById(R.id.presentButton)
        private val absentButton: Button = itemView.findViewById(R.id.absentButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val forecastButton: Button = itemView.findViewById(R.id.forecastButton)

        fun bind(subject: Subject) {
            nameText.text = subject.name
            val percent = if (subject.totalClasses > 0)
                (subject.attendedClasses.toDouble() / subject.totalClasses * 100).toInt()
            else 0
            percentText.text = "$percent%"

            val absent = subject.totalClasses - subject.attendedClasses
            totalText.text = "Total: ${subject.totalClasses} | Present: ${subject.attendedClasses} | Absent: $absent"

            presentButton.setOnClickListener {
                subject.attendedClasses++
                subject.totalClasses++
                viewModel.update(subject)
            }

            absentButton.setOnClickListener {
                subject.totalClasses++
                viewModel.update(subject)
            }

            deleteButton.setOnClickListener {
                viewModel.delete(subject)
            }

            forecastButton.setOnClickListener {
                val layout = LinearLayout(itemView.context).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(50, 40, 50, 10)
                }

                val input = EditText(itemView.context).apply {
                    hint = "Enter remaining classes"
                    inputType = InputType.TYPE_CLASS_NUMBER
                }

                layout.addView(input)

                AlertDialog.Builder(itemView.context)
                    .setTitle("Attendance Forecast")
                    .setView(layout)
                    .setPositiveButton("Check") { _, _ ->
                        val future = input.text.toString().toIntOrNull() ?: 0
                        val target = 75.0

                        if (future <= 0) {
                            AlertDialog.Builder(itemView.context)
                                .setTitle("Forecast Result")
                                .setMessage("❌ Please enter a valid number of remaining classes.")
                                .setPositiveButton("OK", null)
                                .show()
                            return@setPositiveButton
                        }

                        val needed = AttendanceUtils.minRequiredAttendance(
                            subject.attendedClasses,
                            subject.totalClasses,
                            future,
                            target
                        )

                        val message = if (needed == -1) {
                            val maxPercent = AttendanceUtils.maxPossibleAttendance(
                                subject.attendedClasses,
                                subject.totalClasses,
                                future
                            )
                            if (maxPercent.isNaN() || maxPercent.isInfinite() || future == 0) {
                                "❌ You cannot reach 75%. No future classes available or invalid values."
                            } else {
                                val displayPercent = String.format("%.2f", maxPercent.coerceAtMost(100.0))
                                "❌ You cannot reach 75%. Max possible is $displayPercent%"
                            }
                        } else {
                            "✅ You must attend at least $needed of next $future classes to reach 75%"
                        }

                        AlertDialog.Builder(itemView.context)
                            .setTitle("Forecast Result")
                            .setMessage(message)
                            .setPositiveButton("OK", null)
                            .show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }
}
