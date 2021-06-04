package com.medzin.board_game_collector.database

import android.database.sqlite.SQLiteDatabase
import com.medzin.board_game_collector.database.objects.Person
import com.medzin.board_game_collector.util.PersonParser

class ArtistDBHelper {

    companion object {
        val TABLE_ARTISTS = "artists"
        val COLUMN_ID = "id"
        val COLUMN_GAME_ID = "game_id"
        val COLUMN_ARTISTS_LIST = "artists_list"

        fun onCreate(db: SQLiteDatabase) {
            val CREATE_ARTISTS_TABLE = ("CREATE TABLE " + TABLE_ARTISTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_GAME_ID + " INTEGER," +
                    COLUMN_ARTISTS_LIST + " TEXT," + "FOREIGN KEY(" + COLUMN_GAME_ID +
                    ") REFERENCES " + GameDBHandler.TABLE_GAMES + "(" + GameDBHandler.COLUMN_ID + "))")
            db.execSQL(CREATE_ARTISTS_TABLE)
        }

        fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("Not yet implemented")
        }

        fun findArtists(gameId: Int, db: SQLiteDatabase): MutableList<Person> {
            val query = "SELECT * FROM $TABLE_ARTISTS WHERE $COLUMN_GAME_ID = ?"
            val cursor = db.rawQuery(query, arrayOf(gameId.toString()))

            if(cursor.moveToFirst()){
                val resultList = PersonParser.parsePersonList(cursor.getString(cursor.getColumnIndex(
                    COLUMN_ARTISTS_LIST
                )))
                cursor.close()
                return resultList
            }
            cursor.close()
            return mutableListOf()
        }

        fun deleteArtists(gameId: Int, db: SQLiteDatabase) {
            db.delete(TABLE_ARTISTS, "$COLUMN_GAME_ID = ?", arrayOf(gameId.toString()))
        }
    }
}