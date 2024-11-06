package com.unlam.mav.ktor.ui.galleryscreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.unlam.mav.ktor.domain.model.MarvelCharacter

@Composable
fun GalleryItem(
    modifier: Modifier = Modifier,
    character: MarvelCharacter,
    onClick: (MarvelCharacter) -> Unit = {}
) {
    var showDetails by remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = modifier
            .clickable {
                showDetails = !showDetails
                onClick(character) // en un futuro aquí activo la pantalla de detalles
            }
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = character.thumbnail,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(red = 0, green = 0, blue = 0, alpha = 170)
                    ).padding(4.dp),
                fontSize = 25.sp,
                color = Color.White,
                text = character.name,
                minLines = 2,
                maxLines = 2,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
            )
        }
        AnimatedVisibility(showDetails) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = character.description,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun GalleryGrid(
    modifier: Modifier = Modifier,
    characters: List<MarvelCharacter>,
    onClick: (MarvelCharacter) -> Unit = {},
    onGridEnd: () -> Unit = {}
) {
    val gridState = rememberLazyGridState()
    //buffer tiene que ser mayor o igual a 1
    val buffer = 2
    // observa si la lista ha llegado al fin. DerivedStateOf se ha usado para que no recomponga muchas veces
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == gridState.layoutInfo.totalItemsCount - buffer
        }
    }

    LaunchedEffect(reachedBottom) {
        if(reachedBottom) onGridEnd()
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(205.dp),
        state = gridState
    ) {
        items(characters) { character ->
            GalleryItem(
                character = character,
                modifier = Modifier
                    .padding(3.dp)
                    .requiredSize(190.dp),
                onClick = onClick
            )
        }
    }
}
