package com.example.testapp1.interfaces

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp1.R
import com.example.testapp1.database.Student
import com.example.testapp1.database.StudentDatabase

class ViewStudentsActivity : AppCompatActivity() {

    private lateinit var dbHelper: StudentDatabase
    private lateinit var tableLayout: TableLayout
    private var studentList: List<Student> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students)

        dbHelper = StudentDatabase(this)
        tableLayout = findViewById(R.id.tableLayout)
        val btnSort = findViewById<Button>(R.id.btnSort)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val editTextSearch = findViewById<EditText>(R.id.editTextSearch)

        // Load all students initially
        loadStudents(dbHelper.getAllStudents())

        // Click event for sorting
        btnSort.setOnClickListener {
            loadStudents(dbHelper.sortByTotalScore())
        }

        // Click event for searching by Student ID or Course ID
        btnSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(this, "Enter search keyword", Toast.LENGTH_SHORT).show()
            } else {
                val searchResults = dbHelper.searchStudents(query)
                loadStudents(searchResults)
            }
        }
    }

    private fun loadStudents(students: List<Student>) {
        // Ensure previous rows are cleared except the header
        if (tableLayout.childCount > 1) {
            tableLayout.removeViews(1, tableLayout.childCount - 1)
        }

        for (student in students) {
            val tableRow = TableRow(this)

            val idTextView = TextView(this).apply {
                text = student.id.toString()
                setPadding(16, 8, 16, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            }

            val nameTextView = TextView(this).apply {
                text = student.name
                setPadding(16, 8, 16, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f)
            }

            val courseTextView = TextView(this).apply {
                text = student.courseId
                setPadding(16, 8, 16, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            }

            val scoreTextView = TextView(this).apply {
                text = student.score.toString()
                setPadding(16, 8, 16, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            }

            // Add a delete button
            val deleteButton = Button(this).apply {
                text = "Delete"
                setOnClickListener {
                    AlertDialog.Builder(this@ViewStudentsActivity)
                        .setTitle("Delete Student")
                        .setMessage("Are you sure you want to delete ${student.name}?")
                        .setPositiveButton("Yes") { _, _ ->
                            dbHelper.deleteStudent(student.id)
                            loadStudents(dbHelper.getAllStudents()) // Refresh list
                            Toast.makeText(this@ViewStudentsActivity, "Student Deleted", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("No", null)
                        .show()
                }
            }

            // Add views to the row
            tableRow.addView(idTextView)
            tableRow.addView(nameTextView)
            tableRow.addView(courseTextView)
            tableRow.addView(scoreTextView)
            tableRow.addView(deleteButton)

            // Add row to the table
            tableLayout.addView(tableRow)
        }
    }
}
