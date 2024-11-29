package com.example.app.winsaved

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SavedPlacesDbHelper(val context: Context) : SQLiteOpenHelper(context, "appbditemfav", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createSavedTable = """
            CREATE TABLE saveditems (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                login TEXT,
                image TEXT, 
                title TEXT, 
                description TEXT
            )
        """.trimIndent()

        db.execSQL(createSavedTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS saveditems")
        onCreate(db)
    }

    fun addFavoritePlaceForUser(savedPlace: SavedPlaces): Int? {
        val values = ContentValues().apply {
            put("login", savedPlace.login)
            put("image", savedPlace.image)
            put("title", savedPlace.title)
            put("description", savedPlace.desc)
        }

        writableDatabase.use { db ->
            val result = db.insert("saveditems", null, values).toInt()
            return if (result == -1) null else result
        }
    }

    fun deleteFavoritePlaceById(id: Int) {
        val db = this.writableDatabase
        db.delete("saveditems", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun getFavoritePlaceByLogin(login: String): MutableList<SavedPlaces> {
        val savedList = mutableListOf<SavedPlaces>()
        val query = "SELECT * FROM saveditems WHERE login = ?"

        readableDatabase.rawQuery(query, arrayOf(login)).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val image = cursor.getString(cursor.getColumnIndex("image"))
                    val title = cursor.getString(cursor.getColumnIndex("title"))
                    val description = cursor.getString(cursor.getColumnIndex("description"))

                    val savedPlace = SavedPlaces(id, login, image, title, description)
                    savedList.add(savedPlace)
                } while (cursor.moveToNext())
            }
        }
        return savedList
    }

    fun isPlaceFavoriteForUser(login: String, title: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM saveditems WHERE login = ? AND title = ?"
        val cursor = db.rawQuery(query, arrayOf(login, title))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

}