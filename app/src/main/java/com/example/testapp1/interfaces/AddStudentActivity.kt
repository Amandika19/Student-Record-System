package com.example.testapp1.interfaces
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp1.R
import com.example.testapp1.database.StudentDatabase

class AddStudentActivity : AppCompatActivity() {

    private lateinit var dbHelper: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        dbHelper = StudentDatabase(this)

        val nameEditText = findViewById<EditText>(R.id.editTextName)
        val courseIdEditText = findViewById<EditText>(R.id.editTextCourseID)
        val scoreEditText = findViewById<EditText>(R.id.editTextScore)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = nameEditText.text.toString()
            val courseId = courseIdEditText.text.toString()
            val scoreText = scoreEditText.text.toString()

            if (name.isNotEmpty() && courseId.isNotEmpty() && scoreText.isNotEmpty()) {
                val score = scoreText.toInt()
                dbHelper.addStudent(name, courseId, score)
                Toast.makeText(this, "Student Added!", Toast.LENGTH_SHORT).show()
                finish() // Return to the main menu
            } else {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
