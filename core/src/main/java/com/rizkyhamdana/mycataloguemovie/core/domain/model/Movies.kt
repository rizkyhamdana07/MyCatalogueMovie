package com.rizkyhamdana.mycataloguemovie.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movies (
    val id: Int,
    val title: String,
    val genres: String,
    val date: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val voteAverage: String,
    val isFavorite: Boolean
): Parcelable