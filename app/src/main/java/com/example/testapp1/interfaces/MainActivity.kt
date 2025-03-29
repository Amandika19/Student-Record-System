package com.example.testapp1.interfaces

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp1.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd = findViewById<Button>(R.id.btnAddStudent)
        val btnView = findViewById<Button>(R.id.btnViewStudents)
//        val btnSearch = findViewById<Button>(R.id.btnSearchStudent)
//        val btnDelete = findViewById<Button>(R.id.btnDeleteStudent)
//        val btnSort = findViewById<Button>(R.id.btnSortStudents)
        val btnExit = findViewById<Button>(R.id.btnExit)

        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

        btnView.setOnClickListener {
            startActivity(Intent(this, ViewStudentsActivity::class.java))
        }

//        btnSearch.setOnClickListener {
//            startActivity(Intent(this, SearchStudentActivity::class.java))
//        }
//
//        btnDelete.setOnClickListener {
//            startActivity(Intent(this, DeleteStudentActivity::class.java))
//        }
//
//        btnSort.setOnClickListener {
//            startActivity(Intent(this, SortStudentsActivity::class.java))
//        }

        btnExit.setOnClickListener {
            finish()
        }
    }
}
