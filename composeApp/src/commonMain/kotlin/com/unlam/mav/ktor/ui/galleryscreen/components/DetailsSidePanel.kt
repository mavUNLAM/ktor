package com.unlam.mav.ktor.ui.galleryscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.unlam.mav.ktor.domain.model.MarvelCharacter

@Composable
fun DetailsSidePanel(
    modifier: Modifier = Modifier,
    character: MarvelCharacter,
    isLoading: Boolean = true
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if(isLoading) {
                CircularProgressIndicator()
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    model = character.thumbnail,
                    contentDescription = character.name
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = character.name
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = character.description
                    )
                }
            }
        }
    }
}
