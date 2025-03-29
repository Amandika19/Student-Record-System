package com.example.testapp1.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Student(
    val id: Int,
    val name: String,
    val courseId: String,
    val score: Int
)

class StudentDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentRecords.db"
        private const val DATABASE_VERSION = 1

        // Table Name & Columns
        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_COURSE_ID = "course_id"
        private const val COLUMN_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_COURSE_ID TEXT NOT NULL,
                $COLUMN_SCORE INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // **1. Insert a Student Record**
    fun addStudent(name: String, courseId: String, score: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_COURSE_ID, courseId)
            put(COLUMN_SCORE, score)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L // Returns true if insertion was successful
    }

    // **2. Delete a Student Record by ID**
    fun deleteStudent(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0 // Returns true if deletion was successful
    }

    // **3. Retrieve All Student Records**
    fun getAllStudents(): List<Student> {
        val studentList = mutableListOf<Student>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val courseId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_ID))
                val score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
                studentList.add(Student(id, name, courseId, score))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentList
    }

    // **4. Search Student by Student ID**
    fun searchByStudentId(id: Int): List<Student> {
        val studentList = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME, null,
            "$COLUMN_ID=?", arrayOf(id.toString()),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val courseId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_ID))
            val score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
            studentList.add(Student(id, name, courseId, score))
        }
        cursor.close()
        db.close()
        return studentList
    }

    // **5. Search Student by Course ID**
    fun searchByCourseId(courseId: String): List<Student> {
        val studentList = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME, null,
            "$COLUMN_COURSE_ID=?", arrayOf(courseId),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
                studentList.add(Student(id, name, courseId, score))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentList
    }

    // **6. Sort Students by Score (Descending)**
    fun sortByTotalScore(): List<Student> {
        val studentList = mutableListOf<Student>()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_SCORE DESC"
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val courseId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_ID))
                val score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
                studentList.add(Student(id, name, courseId, score))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentList
    }

    fun searchStudents(query: String): List<Student> {
        val studentList = mutableListOf<Student>()
        val db = readableDatabase

        val sqlQuery = """
        SELECT * FROM $TABLE_NAME WHERE 
        $COLUMN_ID LIKE ? OR 
        $COLUMN_NAME LIKE ? OR 
        $COLUMN_COURSE_ID LIKE ? OR 
        $COLUMN_SCORE LIKE ?
    """

        val cursor = db.rawQuery(sqlQuery, arrayOf("%$query%", "%$query%", "%$query%", "%$query%"))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val courseId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_ID))
                val score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
                studentList.add(Student(id, name, courseId, score))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return studentList
    }

}
