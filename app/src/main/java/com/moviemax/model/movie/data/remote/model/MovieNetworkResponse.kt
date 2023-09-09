package com.moviemax.model.movie.data.remote.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("pages")
    val pages: Int = 0,
    @SerializedName("total")
    val total: String?,
    @SerializedName("tv_shows")
    val tvShows: List<TvShow>?
)

data class TvShow(
    @SerializedName("countdown")
    val countdown: Any?,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("episodes")
    val episodes: List<Episode>?,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_path")
    val imagePath: String,
    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: String,
    @SerializedName("pictures")
    val pictures: List<String>,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("rating_count")
    val ratingCount: String?,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("status")
    val status: String
)

data class Episode(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("season")
    val season: Int
)

data class MovieDetailsResponse(
    @SerializedName("tvShow")
    val tvShow: TvShow?
)