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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.common.R
import com.common.ui.components.icon.IconWithDrawable
import com.common.ui.components.text.TextView
import com.common.ui.components.text.TitleTextView
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.getFakeMovies
import com.common.ui.theme.GetColors.movieCardNetworkColor
import com.common.ui.theme.GetColors.movieCardStatusColor
import com.common.ui.theme.spacing
import com.common.util.UiImage
import com.common.util.UiText

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
                    TitleTextView(
                        text = UiText.PlainString(movie.name),
                        textStyle = MaterialTheme.typography.titleLarge,
                        fontColor = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(lineSpace))
                    TextView(
                        text = UiText.PlainString("${movie.network}[${movie.country}]"),
                        textStyle = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        fontColor = movieCardNetworkColor(isDarkMode),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(lineSpace))
                    TextView(
                        text = UiText.PlainString("Started on : ${movie.startDate}"),
                        textStyle = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(lineSpace))
                    TextView(
                        text = UiText.PlainString(movie.status),
                        textStyle = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Light,
                        fontColor = movieCardStatusColor(isDarkMode),
                        fontSize = 10.sp
                    )
                }
            }
        }

        Box {
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

            IconWithDrawable(
                icon = UiImage.DrawableResource(
                    if(movie.isFavourite) R.drawable.ic_favorite_active else R.drawable.ic_favorite_inactive
                ),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(MaterialTheme.spacing.large)
            )
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = getFakeMovies()[0]
    MovieCard(movie = movie)
}