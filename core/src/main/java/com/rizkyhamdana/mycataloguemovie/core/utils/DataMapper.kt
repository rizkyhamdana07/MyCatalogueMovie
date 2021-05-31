package com.rizkyhamdana.mycataloguemovie.core.utils

import com.rizkyhamdana.mycataloguemovie.core.data.source.local.entity.MovieEntity
import com.rizkyhamdana.mycataloguemovie.core.data.source.local.entity.TvShowEntity
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.DetailsMovieResponse
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.DetailsTvResponse
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.ResultsMovie
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.ResultsTv
import com.rizkyhamdana.mycataloguemovie.core.domain.model.Movies
import com.rizkyhamdana.mycataloguemovie.core.domain.model.TvShow

object DataMapper {

    fun mapListMovieResponseToEntities(input: List<ResultsMovie>): List<MovieEntity>{
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movies = MovieEntity(
                it.id,
                it.title,
                "",
                it.releaseDate,
                "",
                " ",
                it.posterPath,
                "",
                false
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapDetailsMovieToEntities(input: DetailsMovieResponse): MovieEntity {
        var detailGenre = ""
        for (genre in input.genres) {
            detailGenre += if (genre == input.genres[input.genres.size - 1]) {
                "${genre.name}."
            } else {
                "${genre.name}, "
            }
        }
        return MovieEntity(
            input.id,
            input.title,
            detailGenre,
            input.releaseDate,
            input.overview,
            input.backdropPath,
            input.posterPath,
            input.voteAverage.toString(),
            false
        )
    }

    fun mapListTvResponseToEntities(input: List<ResultsTv>): List<TvShowEntity>{
        val tvShowList = ArrayList<TvShowEntity>()
        input.map {
            val tvShow = TvShowEntity(
                it.id,
                it.name,
                "",
                it.firstAirDate,
                "",
                "",
                it.posterPath,
                "",
                false
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapDetailsTvToEntities(input: DetailsTvResponse): TvShowEntity {
        var detailGenre = ""
        for (genre in input.genres) {
            detailGenre += if (genre == input.genres[input.genres.size - 1]) {
                "${genre.name}."
            } else {
                "${genre.name}, "
            }
        }
        return TvShowEntity(
            input.id,
            input.name,
            detailGenre,
            input.firstAirDate,
            input.overview,
            input.backdropPath,
            input.posterPath,
            input.voteAverage.toString(),
            false
        )
    }

    fun mapMovieEntitiesToDomain(input: MovieEntity): Movies {
        return Movies(
            input.id,
            input.title,
            input.genres,
            input.date,
            input.overview,
            input.backdropPath,
            input.posterPath,
            input.voteAverage,
            input.isFavorite
        )
    }

    fun mapTvEntitiesToDomain(input: TvShowEntity): TvShow {
        return TvShow(
            input.id,
            input.title,
            input.genres,
            input.date,
            input.overview,
            input.backdropPath,
            input.posterPath,
            input.voteAverage,
            input.isFavorite
        )
    }

    fun mapListMovieEntitiesToDomain(input: List<MovieEntity>): List<Movies>{
        val movieList = ArrayList<Movies>()
        input.map {
            val movies = Movies(
                it.id,
                it.title,
                it.genres,
                it.date,
                it.overview,
                it.backdropPath,
                it.posterPath,
                it.voteAverage,
                it.isFavorite
            )
            movieList.add(movies)
        }
        return movieList
    }

    fun mapListTvEntitiesToDomain(input: List<TvShowEntity>): List<TvShow>{
        val tvShowList = ArrayList<TvShow>()
        input.map {
            val tvShow = TvShow(
                it.id,
                it.title,
                it.genres,
                it.date,
                it.overview,
                it.backdropPath,
                it.posterPath,
                it.voteAverage,
                it.isFavorite
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapMovieDomainToEntities(input: Movies) = MovieEntity(
        input.id,
        input.title,
        input.genres,
        input.date,
        input.overview,
        input.backdropPath,
        input.posterPath,
        input.voteAverage,
        input.isFavorite
    )

    fun mapTvDomainToEntities(input: TvShow) = TvShowEntity(
        input.id,
        input.title,
        input.genres,
        input.date,
        input.overview,
        input.backdropPath,
        input.posterPath,
        input.voteAverage,
        input.isFavorite
    )
}