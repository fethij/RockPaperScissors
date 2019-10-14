package com.example.rockpaperscissor

import android.content.Context

/*
Suspend keyword will make sure that the methods cannot be called without Coroutines.
 */
class GameRepository(context: Context) {

    private var gameDao: GameDao

    init {
        val reminderRoomDatabase = AppDatabase.getDatabase(context)
        gameDao = reminderRoomDatabase!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    suspend fun getWins(): Int {
       return gameDao.getWins()
    }

    suspend fun getDraws(): Int {
        return gameDao.getDraws()
    }

    suspend fun getLosses(): Int {
        return gameDao.getLosses()
    }

}
