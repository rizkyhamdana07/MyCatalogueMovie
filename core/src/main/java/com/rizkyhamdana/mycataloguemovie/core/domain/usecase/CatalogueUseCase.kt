package com.rizkyhamdana.mycataloguemovie.core.domain.usecase

import com.rizkyhamdana.mycataloguemovie.core.data.Resource
import com.rizkyhamdana.mycataloguemovie.core.domain.model.Movies
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface CatalogueUseCase {

    fun getMoviePopular(): Flow<Resource<List<Movies>>>

    fun getTvPopular(): Flow<Resource<List<TvShow>>>

    fun getDetailsMovie(id: Int) : Flow<Resource<Movies>>

    fun getDetailsTv(id: Int) : Flow<Resource<TvShow>>

    fun getMovieFavorite(): Flow<List<Movies>>

    fun getTvFavorite(): Flow<List<TvShow>>

    fun setMovieFavorite(movies: Movies, state: Boolean)

    fun setTvFavorite(tvShow: TvShow, state: Boolean)

}