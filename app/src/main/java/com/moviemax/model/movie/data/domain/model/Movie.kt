package com.moviemax.model.movie.data.domain.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val country: String,
    val description: String?,
    val endDate: String? =null,
    var movieEpisodes: List<MovieEpisode>? = listOf(),
    val genres: List<String>? = listOf(),
    val id: Int,
    val imagePath: String?,
    val imageThumbnailPath: String,
    val name: String,
    val pictures: List<String>?,
    val rating: String?,
    val ratingCount: String?,
    val runtime: Int,
    val startDate: String,
    val status: String,
    val url: String? = null,
    val network: String,
    val isFavourite:Boolean =false
)

data class MovieEpisode(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("season")
    val season: Int
)