package com.rizkyhamdana.mycataloguemovie.core.data.source.local

import com.rizkyhamdana.mycataloguemovie.core.data.source.local.entity.MovieEntity
import com.rizkyhamdana.mycataloguemovie.core.data.source.local.entity.TvShowEntity
import com.rizkyhamdana.mycataloguemovie.core.data.source.local.room.CatalogueDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LocalDataSource(private val catalogueDao: CatalogueDao){

    suspend fun insertMovie(movieEntity: List<MovieEntity>) = catalogueDao.insertMovie(movieEntity)

    suspend fun insertTv(tvShowEntity: List<TvShowEntity>) = catalogueDao.insertTv(tvShowEntity)

    fun getAllMovie(): Flow<List<MovieEntity>> = catalogueDao.getAllMovie()

    fun getDetailsMovie(id: Int): Flow<MovieEntity> = catalogueDao.getDetailsMovie(id)

    fun getDetailsTv(id: Int): Flow<TvShowEntity> = catalogueDao.getDetailsTv(id)

    fun getAllTvShow(): Flow<List<TvShowEntity>> = catalogueDao.getAllTvShow()

    fun getMovieFavorite(): Flow<List<MovieEntity>> = catalogueDao.getMovieFavorite()

    fun getTvShowFavorite(): Flow<List<TvShowEntity>> = catalogueDao.getTvFavorite()

    suspend fun updateMovie(movie: MovieEntity) = catalogueDao.updateMovie(movie)

    suspend fun updateTv(tvShow: TvShowEntity) = catalogueDao.updateTv(tvShow)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        movie.isFavorite = newState
        CoroutineScope(Dispatchers.IO).launch {
            catalogueDao.updateMovie(movie)
        }
    }

    fun setFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean){
        tvShow.isFavorite = newState
        CoroutineScope(Dispatchers.IO).launch {
            catalogueDao.updateTv(tvShow)
        }
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(catalogueDao).apply { INSTANCE = this }
    }
}