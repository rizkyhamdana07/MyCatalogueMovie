package com.rizkyhamdana.mycataloguemovie.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_tvshow")
data class TvShowEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String,

    @ColumnInfo(name = "posterPath")
    var posterPath: String,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false

)