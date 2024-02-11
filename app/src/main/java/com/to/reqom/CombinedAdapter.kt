package com.to.reqom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CombinedAdapter(private var combinedList: List<com.to.reqom.Movie>, private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<CombinedAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = combinedList[position]
        if(item.poster_path == null){

        }
        else {
            Picasso.get().load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .into(holder.posterImageView)
            holder.titleTextView.text = item.title
            holder.itemView.setOnClickListener {
                onItemClick(item.id.toInt())
            }
        }
    }

    override fun getItemCount(): Int {
        return combinedList.size
    }

    fun submitList(newCombinedList: List<Movie>) {
        combinedList = newCombinedList
        notifyDataSetChanged()
    }
}
