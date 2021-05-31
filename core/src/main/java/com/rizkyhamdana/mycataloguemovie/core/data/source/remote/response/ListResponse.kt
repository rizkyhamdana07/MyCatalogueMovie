package com.rizkyhamdana.mycataloguemovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @field:SerializedName("results")
    val results: List<T>
)

data class ResultsMovie(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    )

data class ResultsTv(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    )
