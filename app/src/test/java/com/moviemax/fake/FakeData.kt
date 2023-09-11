package com.moviemax.fake

import com.moviemax.model.movie.data.remote.model.Episode
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.data.remote.model.TvShow

val movieId = 29561

private val fakeTvShowResponse: MoviesResponse
    get() = MoviesResponse(
        total = "5",
        page = 1,
        pages = 1,
        tvShows = getFakeMoviesTest()
    )

private val movieTemp = TvShow(
    id = 29560,
    name = "Arrow",
    country = "India",
    description = "Arrow is an American television series developed by writer/producers Greg Berlanti, Marc Guggenheim, and Andrew Kreisberg. It is based on the DC Comics character Green Arrow, a costumed crime-fighter created by Mort Weisinger and George Papp. It premiered in North America on The CW on October 10, 2012, with international broadcasting taking place in late 2012. Primarily filmed in Vancouver, British Columbia, Canada, the series follows billionaire playboy Oliver Queen, portrayed by Stephen Amell, who, five years after being stranded on a hostile island, returns home to fight crime and corruption as a secret vigilante whose weapon of choice is a bow and arrow. Unlike in the comic books, Queen does not go by the alias \\\"Green Arrow\\\". The series takes a realistic look at the Green Arrow character, as well as other characters from the DC Comics universe. Although Oliver Queen/Green Arrow had been featured in the television series Smallville from 2006 to 2011, the producers decided to start clean and find a new actor (Amell) to portray the character. Arrow focuses on the humanity of Oliver Queen, and how he was changed by time spent shipwrecked on an island. Most episodes have flashback scenes to the five years in which Oliver was missing.",
    startDate = "2012-10-10",
    status = "Ended",
    runtime = 60,
    network = "The CW",
    imagePath = "https://static.episodate.com/images/tv-show/full/29560.jpg",
    imageThumbnailPath = "https://static.episodate.com/images/tv-show/thumbnail/29560.jpg",
    rating = "9.1157",
    ratingCount = "648",
    pictures = listOf(
        "https://static.episodate.com/images/episode/29560-242.jpg",
        "https://static.episodate.com/images/episode/29560-804.jpg",
        "https://static.episodate.com/images/episode/29560-664.jpg",
        "https://static.episodate.com/images/episode/29560-120.jpg",
        "https://static.episodate.com/images/episode/29560-764.jpg",
        "https://static.episodate.com/images/episode/29560-792.jpg",
        "https://static.episodate.com/images/episode/29560-159.jpg"
    ),
    countdown = null,
    endDate = null,
    episodes = listOf<Episode>(
        Episode(
            season = 1,
            episode = 17,
            name = "The Huntress Returns",
            airDate = "2013-03-21 00:00:00"
        )
    ),
    genres = listOf("Drama", "Action", "Science-Fiction"),
)

val fakeMoviesList = listOf<TvShow>(
    movieTemp,
    movieTemp.copy(id = 29561),
    movieTemp.copy(id = 29562),
    movieTemp.copy(id = 29563),
    movieTemp.copy(id = 29564),
    movieTemp.copy(id = 29565),
    movieTemp.copy(id = 29566),
    movieTemp.copy(id = 29567),
    movieTemp.copy(id = 29568),
    movieTemp.copy(id = 29569),
)

fun getFakeMovieTest(movieId: Int): TvShow? = fakeMoviesList.findLast {
    it.id == movieId
}

fun getFakeMoviesTest() = fakeMoviesList

fun getFakeMoviesTestWithError() = MoviesResponse(
    total = "5",
    page = 1,
    pages = 1,
    tvShows = null
)

fun getMovieResponseTest() = fakeTvShowResponse

fun getMovieDetailsResponseTest(movieId: Int) =
    MovieDetailsResponse(getFakeMovieTest(movieId))

fun getMovieDetailsResponseTestWithError() =
    MovieDetailsResponse(tvShow = null)