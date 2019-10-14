package com.example.rockpaperscissor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1

    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        gameRepository = GameRepository(this)
        initViews()
    }

    /**
     * Initializes view elements.
     *
     */
    private fun initViews() {
        initListeners()
        updateStats()
    }

    /**
     * Updates the statistics by retrieving the data from the database.
     *
     */
    private fun updateStats() {
        CoroutineScope(Dispatchers.Main).launch {
            val wins = withContext(Dispatchers.IO) {
                gameRepository.getWins()
            }
            val draws = withContext(Dispatchers.IO) {
                gameRepository.getDraws()
            }
            val losses = withContext(Dispatchers.IO) {
                gameRepository.getLosses()
            }
            tvStats.text = getString(
                R.string.win_draw_lose,
                wins.toString(),
                draws.toString(),
                losses.toString()
            )
        }
    }

    /**
     * Initializes the onClickListeners.
     *
     */
    private fun initListeners() {
        ibtnHistory.setOnClickListener {
            startActivity()
        }
        ibtnRock.setOnClickListener { handleGame(Gesture.ROCK) }
        ibtnPaper.setOnClickListener { handleGame(Gesture.PAPER) }
        ibtnScissor.setOnClickListener { handleGame(Gesture.SCISSOR) }
    }

    /**
     * Starts the GameHistoryActivity.
     *
     */
    private fun startActivity() {
        val intent = Intent(this, GameHistoryActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    /**
     * Game handler that will simulate the game and call the necessary methods.
     *
     * @param gesture Action that the user has chosen.
     */
    private fun handleGame(gesture: Gesture) {
        val computerAction = (0..3).shuffled().first()
        val computerGesture = assignGesture(computerAction)
        var game = Game(
            null,
            "",
            Date(),
            gesture,
            computerGesture
        )

        game = calculateWinner(game)
        insertGameIntoDatabase(game)
        displayGameResults(game)
        updateStats()
    }

    /**
     * Will insert the game into the database.
     *
     * @param game Game to be inserted.
     */
    private fun insertGameIntoDatabase(game: Game) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }

    /**
     * Will assign a gesture to the given value.
     *
     * @param value To be assigned.
     * @return The assigned gesture.
     */
    private fun assignGesture(value: Int): Gesture {
        return when (value) {
            0 -> Gesture.ROCK
            1 -> Gesture.PAPER
            3 -> Gesture.SCISSOR
            else -> Gesture.ROCK
        }
    }

    /**
     * Will check who the winner is according to the basic game rules.
     *
     * @param game Game that needs the winner calculation.
     * @return Updated game with winner property set.
     */
    private fun calculateWinner(game: Game): Game {
        game.result = when {
            game.computerAction == game.userAction -> getString(R.string.draw)
            game.computerAction == Gesture.ROCK && game.userAction == Gesture.SCISSOR -> getString(R.string.computer_win)
            game.computerAction == Gesture.SCISSOR && game.userAction == Gesture.PAPER -> getString(
                R.string.computer_win
            )
            game.computerAction == Gesture.PAPER && game.userAction == Gesture.ROCK -> getString(R.string.computer_win)
            else -> getString(R.string.you_win)
        }
        return game
    }

    /**
     * Method that will display the game results.
     *
     * @param game Game to be displayed.
     */
    private fun displayGameResults(game: Game) {
        tvResult.text = game.result
        ivComputer.setImageDrawable(getDrawable(game.computerAction.drawableId))
        ivYou.setImageDrawable(getDrawable(game.userAction.drawableId))
    }

    /**
     * Will make sure the stats will be updated and the previous game won't be visible but a new
     * game can be started.
     *
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        updateStats()
        resetGameResults()
    }

    /**
     * Resets the current game view elements.
     *
     */
    private fun resetGameResults() {
        tvResult.text = getString(R.string.initial_result)
        ivComputer.setImageDrawable(getDrawable(Gesture.PAPER.drawableId))
        ivYou.setImageDrawable(getDrawable(Gesture.PAPER.drawableId))
    }
}
