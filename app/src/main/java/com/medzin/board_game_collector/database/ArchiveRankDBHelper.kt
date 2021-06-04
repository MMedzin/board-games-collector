package com.medzin.board_game_collector.database

import android.database.sqlite.SQLiteDatabase
import com.medzin.board_game_collector.database.objects.GameRank
import com.medzin.board_game_collector.database.objects.Person
import com.medzin.board_game_collector.util.DBQuery
import com.medzin.board_game_collector.util.PersonParser

class ArchiveRankDBHelper {

    companion object{
        val TABLE_RANKS = "ranks"
        val COLUMN_GAME_ID = "game_id"
        val COLUMN_DATE = "since_date"
        val COLUMN_RANK = "rank"

        fun onCreate(db: SQLiteDatabase) {
            val CREATE_RANKS_TABLE = ("CREATE TABLE " + TABLE_RANKS + "(" +
                    COLUMN_GAME_ID + " INTEGER," + COLUMN_DATE + " TEXT, " +
                    COLUMN_RANK + " INTEGER," + "PRIMARY KEY(" +
                    COLUMN_GAME_ID + ", " + COLUMN_DATE + ", " + COLUMN_RANK + "), " +
                    "FOREIGN KEY(" + COLUMN_GAME_ID +
                    ") REFERENCES " + GameDBHandler.TABLE_GAMES + "(" + GameDBHandler.COLUMN_ID + "))")
            db.execSQL(CREATE_RANKS_TABLE)
        }

        fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("Not yet implemented")
        }

        fun findRanks(gameId: Int, db: SQLiteDatabase): MutableList<GameRank> {
            val query = "SELECT * FROM $TABLE_RANKS WHERE $COLUMN_GAME_ID = ?"
            val cursor = db.rawQuery(query, arrayOf(gameId.toString()))
            var ranks = mutableListOf<GameRank>()

            while(cursor.moveToNext()){
                ranks.add(GameRank(DBQuery.queryNullableInt(cursor, COLUMN_RANK),
                    DBQuery.queryDate(cursor, COLUMN_DATE)))
            }
            cursor.close()
            return ranks
        }

        fun deleteRanks(gameId: Int, db: SQLiteDatabase) {
            db.delete(TABLE_RANKS, "$COLUMN_GAME_ID = ?", arrayOf(gameId.toString()))
        }
    }

}