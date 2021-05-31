package com.rizkyhamdana.mycataloguemovie.core.data.source.remote.api

import com.rizkyhamdana.mycataloguemovie.core.BuildConfig
import com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ListResponse<ResultsMovie>

    @GET("tv/popular")
    suspend fun getPopularTvShow(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ListResponse<ResultsTv>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId : Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): DetailsMovieResponse

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): DetailsTvResponse


}