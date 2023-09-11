package com.moviemax.view.movie.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.getFakeMovies
import com.common.ui.theme.GetColors.movieCardNetworkColor
import com.common.ui.theme.GetColors.movieCardStatusColor

@Composable
fun MovieCard(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    movie: Movie,
    onItemClicked: (item: Movie) -> Unit ={}
) {
    val cardRadios = 8.dp
    val lineSpace = 4.dp
    Box(
        modifier = Modifier
            .testTag("${movie.id}")
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable {
                onItemClicked(movie)
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Card(
                shape = RoundedCornerShape(cardRadios),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.Bottom)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(start = 90.dp)
                ) {
                    Spacer(modifier = Modifier.height(lineSpace))
                    Text(
                        text = movie.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(lineSpace))
                    Text(
                        text = "${movie.network}[${movie.country}]",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = movieCardNetworkColor(isDarkMode)
                    )
                    Spacer(modifier = Modifier.height(lineSpace))
                    Text(
                        text = "Started on : ${movie.startDate}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(lineSpace))
                    Text(
                        text = movie.status,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Light,
                        color = movieCardStatusColor(isDarkMode)
                    )
                }
            }
        }

        AsyncImage(
            model = movie.imageThumbnailPath,
            contentScale = ContentScale.FillBounds,
            contentDescription = movie.name,
            modifier = Modifier
                .width(90.dp)
                .height(115.dp)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(cardRadios)),
        )
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = getFakeMovies()[0]
    MovieCard(movie = movie)
}