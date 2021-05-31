package com.rizkyhamdana.mycataloguemovie.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizkyhamdana.mycataloguemovie.core.BuildConfig
import com.rizkyhamdana.mycataloguemovie.core.R
import com.rizkyhamdana.mycataloguemovie.core.databinding.ListTvFavoriteBinding
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow

class FavoriteTvShowAdapter : RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowViewHolder>() {

    private var listTv = ArrayList<TvShow>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }

    fun seTvShow(tv: List<TvShow>?) {
        if (tv == null) return
        this.listTv.clear()
        this.listTv.addAll(tv)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTvShowViewHolder {
        val binding = ListTvFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteTvShowViewHolder, position: Int) {
        val tvShow = listTv[position]
        holder.bind(tvShow)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listTv[position])
        }
    }

    override fun getItemCount(): Int = listTv.size


    class FavoriteTvShowViewHolder(private val binding: ListTvFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvShow) {
            with(binding) {
                tvTitle.text = tv.title
                tvItemDate.text = tv.date
                tvOverview.text = tv.overview
                val imgUrl = BuildConfig.IMAGE_URL + tv.posterPath
                Glide.with(itemView.context)
                    .load(imgUrl)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }
}