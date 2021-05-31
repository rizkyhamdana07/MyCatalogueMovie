package com.rizkyhamdana.mycataloguemovie.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizkyhamdana.mycataloguemovie.core.BuildConfig
import com.rizkyhamdana.mycataloguemovie.core.R
import com.rizkyhamdana.mycataloguemovie.core.databinding.ListTvShowBinding
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private var listTv = ArrayList<TvShow>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }

    fun setTvShow(tv: List<TvShow>?) {
        if (tv == null) return
        this.listTv.clear()
        this.listTv.addAll(tv)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = ListTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = listTv[position]
        holder.bind(tvShow)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listTv[position])
        }
    }

    override fun getItemCount(): Int = listTv.size


    class TvShowViewHolder(private val binding: ListTvShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvShow) {
            with(binding) {
                tvItemTitle.text = tv.title
                tvItemDate.text = tv.date
                val imgUrl: String = BuildConfig.IMAGE_URL + tv.posterPath
                Glide.with(itemView.context)
                    .load(imgUrl)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }
}