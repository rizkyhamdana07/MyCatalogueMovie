package com.rizkyhamdana.mycataloguemovie.movie.details

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
import com.rizkyhamdana.mycataloguemovie.core.domain.model.Movies
import com.rizkyhamdana.mycataloguemovie.databinding.ActivityDetailsMovieBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMovieBinding
    private val detailsMovieViewModel: DetailsMovieViewModel  by viewModel()
    private var stateFavorite = false


    companion object{
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        detailsMovieViewModel.detailsMovie(id).observe(this, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        binding.fbFavorite.visibility = View.VISIBLE
                        setMovieLayout(movies.data!!)
                        stateFavorite = movies.data!!.isFavorite
                        setFavorite(stateFavorite)
                        binding.fbFavorite.setOnClickListener{
                            if (!stateFavorite) {
                                stateFavorite = true
                                detailsMovieViewModel.setFavoriteMovie(movies.data!!, stateFavorite)
                                Toast.makeText(this, getString(R.string.favorite_success), Toast.LENGTH_SHORT).show()
                            }
                            else {
                                val builder = AlertDialog.Builder(this)
                                builder.setNegativeButton(getString(R.string.no)) { _, _ ->}
                                builder.setTitle(getString(R.string.cancel_title))
                                builder.setMessage(getString(R.string.cancel_message))
                                builder.setPositiveButton(R.string.yes){_, _ ->
                                    stateFavorite = false
                                    detailsMovieViewModel.setFavoriteMovie(movies.data!!, stateFavorite)
                                    Toast.makeText(this, getString(R.string.cancel_favorite), Toast.LENGTH_SHORT).show()
                                }
                                builder.create().show()
                            }
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }

                }
            }
        })

    }


    private fun setMovieLayout(data: Movies){
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
                .apply(
                    RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(imgPoster)
            Glide.with(applicationContext)
                .load(backdropUrl)
                .apply(
                    RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
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