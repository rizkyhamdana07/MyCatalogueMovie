package com.rizkyhamdana.mycataloguemovie.core.data.source.local.room

import androidx.room.*
import com.rizkyhamdana.mycataloguemovie.core.data.source.local.entity.MovieEntity
import com.rizkyhamdana.mycataloguemovie.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueDao {
    @Insert(entity = MovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<MovieEntity>)

    @Insert(entity = TvShowEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tvShow : List<TvShowEntity>)

    @Query("SELECT * FROM tb_movie")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tb_movie where id = :id")
    fun getDetailsMovie(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM tb_tvshow where id = :id")
    fun getDetailsTv(id: Int): Flow<TvShowEntity>

    @Query("SELECT * FROM tb_tvshow")
    fun getAllTvShow(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tb_movie WHERE isFavorite = 1")
    fun getMovieFavorite(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tb_tvshow WHERE isFavorite = 1")
    fun getTvFavorite(): Flow<List<TvShowEntity>>

    @Update(entity = MovieEntity::class)
    suspend fun updateMovie(movie: MovieEntity)

    @Update(entity = TvShowEntity::class)
    suspend fun updateTv(tvShow: TvShowEntity)
}