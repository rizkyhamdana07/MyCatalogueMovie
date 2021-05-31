package com.rizkyhamdana.mycataloguemovie.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizkyhamdana.mycataloguemovie.core.BuildConfig
import com.rizkyhamdana.mycataloguemovie.core.R
import com.rizkyhamdana.mycataloguemovie.core.databinding.ListMovieFavoriteBinding
import com.rizkyhamdana.mycataloguemovie.core.domain.model.Movies

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    private var listMovies = ArrayList<Movies>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movies)
    }

    fun setMovies(movies: List<Movies>?) {
        if (movies == null) return
        this.listMovies.clear()
        this.listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding = ListMovieFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val movies = listMovies[position]
        holder.bind(movies)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listMovies[position])
        }
    }

    override fun getItemCount(): Int = listMovies.size


    class FavoriteMovieViewHolder(private val binding: ListMovieFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movies) {
            with(binding) {
                tvTitle.text = movies.title
                tvItemDate.text = movies.date
                tvOverview.text = movies.overview
                val imgUrl = BuildConfig.IMAGE_URL + movies.posterPath
                Glide.with(itemView.context)
                    .load(imgUrl)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }
}