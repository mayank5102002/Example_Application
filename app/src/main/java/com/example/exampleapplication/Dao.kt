package com.example.exampleapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

object Dao {

    private lateinit var db: SQLiteDatabase

    fun init(context: Context) {
        synchronized(this) {
            if (!::db.isInitialized) {
                db = DatabaseHelper(context).writableDatabase
            }
        }
    }

    fun closeDb() {
        db.close()
    }

    fun registerUser(firstName: String, lastName: String, username: String, password: String): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_FIRST_NAME, firstName)
            put(DatabaseHelper.COL_LAST_NAME, lastName)
            put(DatabaseHelper.COL_USERNAME, username)
            put(DatabaseHelper.COL_PASSWORD, password)
        }
        return db.insert(DatabaseHelper.TABLE_NAME, null, values)
    }

    fun userExists(username: String): Boolean {
        val query = "SELECT ${DatabaseHelper.COL_ID} FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COL_USERNAME} = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    @SuppressLint("Range")
    fun login(username: String, password: String): Long? {
        val query = "SELECT ${DatabaseHelper.COL_ID} FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COL_USERNAME} = ? AND ${DatabaseHelper.COL_PASSWORD} = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val userId: Long? = if (cursor.moveToFirst()) {
            cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_ID))
        } else {
            null
        }
        cursor.close()
        return userId
    }

    @SuppressLint("Range")
    fun getUserDetails(userId: Long): User? {
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COL_ID} = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val user: User? = if (cursor.moveToFirst()) {
            User(
                cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FIRST_NAME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LAST_NAME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME))
            )
        } else {
            null
        }
        cursor.close()
        return user
    }

    data class User(
        val userId: Long,
        val firstName: String,
        val lastName: String,
        val username: String
    )
}
