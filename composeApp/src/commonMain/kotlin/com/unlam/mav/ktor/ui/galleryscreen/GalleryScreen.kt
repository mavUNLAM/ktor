package com.unlam.mav.ktor.ui.galleryscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unlam.mav.ktor.domain.model.MarvelCharacter
import com.unlam.mav.ktor.ui.galleryscreen.components.GalleryItem

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: GalleryScreenViewModel,
    onCharacterClick: (MarvelCharacter) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is GalleryScreenState.Loading -> CircularProgressIndicator(modifier = modifier)
        is GalleryScreenState.Success -> GalleryScreenContent(
            modifier = modifier,
            characters = (state as GalleryScreenState.Success).characters,
            onCharacterClick = onCharacterClick,
            onListEndReached = viewModel::loadCharactersV2
        )
        is GalleryScreenState.Error -> Text((state as GalleryScreenState.Error).message)
    }
}

@Composable
fun GalleryScreenContent(
    modifier: Modifier = Modifier,
    characters: List<MarvelCharacter>,
    onCharacterClick: (MarvelCharacter) -> Unit = {},
    onListEndReached: () -> Unit = {}
) {
    Box(modifier = modifier) {
        //insertar imagen de fondo
        //crear buscador
        CharacterList(
            modifier = Modifier.fillMaxWidth(),
            characters = characters,
            onCharacterClick = onCharacterClick,
            onListEndReached = onListEndReached
        )
    }
}

@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<MarvelCharacter>,
    onCharacterClick: (MarvelCharacter) -> Unit = {},
    onListEndReached: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    //buffer tiene que ser mayor o igual a 1
    val buffer = 2
    // observa si la lista ha llegado al fin. DerivedStateOf se ha usado para que no recomponga muchas veces
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - buffer
        }
    }

    // carga más si llega al fin de la lista
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) onListEndReached()
    }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = characters,
            key = { character -> character.id }
        ) {
            GalleryItem(
                modifier = Modifier.padding(8.dp),
                character = it,
                onClick = onCharacterClick
            )
        }
    }
}

