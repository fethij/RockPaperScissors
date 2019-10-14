package com.example.rockpaperscissor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.game_item.view.*


class GameAdapter (private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return games.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    private fun onItemClick(portal: Game) {
        println("$portal.result clicked!")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(game: Game) {
            itemView.tvResult.text = game.result
            itemView.ivComputer.setImageDrawable(getDrawable(itemView.context, game.computerAction.drawableId))
            itemView.ivYou.setImageDrawable(getDrawable(itemView.context, game.userAction.drawableId))
            itemView.tvDate.text = game.date.toLocaleString()
        }

        init {
            itemView.setOnClickListener { onItemClick(games[adapterPosition]) }
        }
    }
}