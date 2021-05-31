package com.rizkyhamdana.mycataloguemovie.tvshow.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizkyhamdana.mycataloguemovie.R
import com.rizkyhamdana.mycataloguemovie.core.BuildConfig
import com.rizkyhamdana.mycataloguemovie.core.data.Resource
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow
import com.rizkyhamdana.mycataloguemovie.databinding.ActivityDetailsTvShowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsTvShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsTvShowBinding
    private val detailsTvShowViewModel: DetailsTvShowViewModel by viewModel()
    private var stateFavorite = false

    companion object{
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsTvShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        detailsTvShowViewModel.detailsTvShow(id).observe(this, { tvShow ->
            if (tvShow != null){
                when(tvShow){
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        binding.fbFavorite.visibility = View.VISIBLE
                        setTvShowLayout(tvShow.data!!)
                        stateFavorite = tvShow.data!!.isFavorite
                        setFavorite(stateFavorite)
                        binding.fbFavorite.setOnClickListener {
                            if (!stateFavorite) {
                                stateFavorite = true
                                detailsTvShowViewModel.setFavoriteTv(tvShow.data!!, stateFavorite)
                                Toast.makeText(this, getString(R.string.favorite_success), Toast.LENGTH_SHORT).show()
                            }
                            else {
                                val builder = AlertDialog.Builder(this)
                                builder.setPositiveButton(R.string.yes){_, _ ->
                                    stateFavorite = false
                                    detailsTvShowViewModel.setFavoriteTv(tvShow.data!!, stateFavorite)
                                    Toast.makeText(this, getString(R.string.cancel_favorite), Toast.LENGTH_SHORT).show()
                                }
                                builder.setNegativeButton(getString(R.string.no)) { _, _ ->}
                                builder.setTitle(getString(R.string.cancel_title))
                                builder.setMessage(getString(R.string.cancel_message))
                                builder.create().show()
                            }
                        }
                    }
                    is Resource.Error -> showLoading(false)
                }
            }
        })


    }

    private fun setTvShowLayout(data: TvShow){
        val backdropUrl = BuildConfig.IMAGE_URL + data.backdropPath
        val posterUrl = BuildConfig.IMAGE_URL + data.posterPath
        with(binding){
            tvTitle.text = data.title
            tvRelease.text = data.date
            tvGenre.text = data.genres
            tvOverview.text = data.overview
            tvVoteAverage.text = data.voteAverage
            Glide.with(applicationContext)
                .load(posterUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(imgPoster)
            Glide.with(applicationContext)
                .load(backdropUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(imgBackdrop)
        }
    }


    private fun setFavorite(stateFavorite: Boolean){
        if (stateFavorite){
            binding.fbFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else{
            binding.fbFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }


    private fun showLoading(state: Boolean){
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }
}