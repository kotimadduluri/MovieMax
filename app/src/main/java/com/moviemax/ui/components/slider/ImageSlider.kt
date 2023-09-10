package com.moviemax.ui.components.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageSlider(images: List<String>) {
    val configuration = LocalConfiguration.current
    val imageWidth = configuration.screenWidthDp.dp
    val imageHeight = configuration.screenHeightDp.dp / 2
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ImageSlider")
    ) {
        itemsIndexed(images) { index, imageUrl ->
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .width(imageWidth)
                        .height(imageHeight)
                        .padding(end = 16.dp)
                        .testTag("picture $index")
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Movie Image $index",
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.FillBounds,
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 8.dp)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        images.forEachIndexed { innerIndex, _ ->
                            val indicatorColor =
                                if (index == innerIndex) MaterialTheme.colorScheme.secondary else Color.Gray
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(indicatorColor)
                                    .padding(5.dp)
                            )
                            Spacer(modifier = Modifier.size(2.dp))
                        }
                    }
                }
            }
        }
    }
}