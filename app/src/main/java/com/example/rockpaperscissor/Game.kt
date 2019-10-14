package com.example.rockpaperscissor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Entity class of the Game model.
 *
 * @property id Unique id of the game.
 * @property result Result of the game, the winner.
 * @property date The date the game has been played on.
 * @property userAction The action that the user chose.
 * @property computerAction The action that the computer chose.
 */
@Entity(tableName = "gameTable")
data class Game (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long? = null,
    @ColumnInfo(name = "result") var result: String,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "userAction") var userAction: Gesture,
    @ColumnInfo(name = "computerAction") var computerAction: Gesture
)