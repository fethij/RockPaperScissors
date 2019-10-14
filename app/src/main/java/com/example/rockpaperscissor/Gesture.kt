package com.example.rockpaperscissor

import android.content.res.Resources

/**
 * Enum for the actions that can be chosen in the game.
 *
 * @property value Integer value that represents the action.
 * @property drawableId Id of the drawable in the resources.
 */
enum class Gesture(val value: Int, val drawableId: Int) {
    ROCK(0, R.drawable.rock),
    PAPER(1, R.drawable.paper),
    SCISSOR(2, R.drawable.scissors);
}