package com.unlam.mav.ktor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.unlam.mav.ktor.domain.model.MarvelCharacter
import com.unlam.mav.ktor.ui.galleryscreen.GalleryScreenState
import com.unlam.mav.ktor.ui.galleryscreen.GalleryScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: GalleryScreenViewModel,
    onCharacterClick: (Int) -> Unit = {}
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

@Preview(showBackground = true)
@Composable
fun GalleryScreenContent(
    modifier: Modifier = Modifier,
    characters: List<MarvelCharacter> = listOf(
    MarvelCharacter(
        id = 0,
        name = "Name 0",
        description = "Description",
        thumbnail = "image.jpg"
    ),
    MarvelCharacter(
        id = 1,
        name = "Name 1",
        description = "Description",
        thumbnail = "image.jpg"
    )
),
    onCharacterClick: (Int) -> Unit = {},
    onListEndReached: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
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

@Preview(showBackground = true)
@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<MarvelCharacter> = listOf(
        MarvelCharacter(
        id = 0,
        name = "Name 0",
        description = "Description",
        thumbnail = "image.jpg"
        ),
        MarvelCharacter(
            id = 1,
            name = "Name 1",
            description = "Description",
            thumbnail = "image.jpg"
        )
    ),
    onCharacterClick: (Int) -> Unit = {},
    onListEndReached: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    //buffer tiene que ser mayor o igual a 1
    val buffer = 1
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
        modifier = modifier.fillMaxWidth(),
        state = listState
    ) {
        items(
            items = characters,
            key = { character -> character.id }
        ) {
            CharacterItem(
                modifier = Modifier.clickable { onCharacterClick(it.id) },
                character = it
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: MarvelCharacter = MarvelCharacter(
        id = 0,
        name = "Name",
        description = "Description",
        thumbnail = "image.jpg"
    )
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = character.thumbnail)
            Text(text = character.name)
            Text(text = character.description)
        }
    }
}
