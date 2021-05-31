package com.rizkyhamdana.mycataloguemovie.core.domain.usecase

import com.rizkyhamdana.mycataloguemovie.core.domain.model.Movies
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow
import com.rizkyhamdana.mycataloguemovie.core.domain.repository.ICatalogueRepository

class CatalogueInteractor(private val catalogueRepository: ICatalogueRepository): CatalogueUseCase {

    override fun getMoviePopular() = catalogueRepository.getMoviePopular()

    override fun getTvPopular()= catalogueRepository.getTvPopular()

    override fun getDetailsMovie(id: Int) = catalogueRepository.getDetailsMovie(id)

    override fun getDetailsTv(id: Int) = catalogueRepository.getDetailsTv(id)

    override fun getMovieFavorite() = catalogueRepository.getMovieFavorite()

    override fun getTvFavorite() = catalogueRepository.getTvFavorite()

    override fun setMovieFavorite(movies: Movies, state: Boolean) = catalogueRepository.setMovieFavorite(movies, state)

    override fun setTvFavorite(tvShow: TvShow, state: Boolean) = catalogueRepository.setTvFavorite(tvShow, state)

}