package com.unlam.mav.ktor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.repository.CharacterRepositoryImp
import com.unlam.mav.ktor.ui.galeryscreen.GalleryScreen
import com.unlam.mav.ktor.ui.galeryscreen.GalleryScreenViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val ktorService = KtorService()
        val repository = CharacterRepositoryImp(ktorService)
        val viewModel = GalleryScreenViewModel(repository = repository)
        GalleryScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel
        )
    }
}

/*
@Composable
fun GreetingView(text: String) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = text
    )
}

 */

/*
MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val scope = rememberCoroutineScope()
                var text by remember { mutableStateOf("Loading") }
                LaunchedEffect(true) {
                    scope.launch {
                        val ktorService = KtorService()
                        val credentials = LogIn().logInNow("user", "password")

                        text = try {
                            //Greeting().greeting()
                            ktorService.getCharacters(page = 1, logIngCredentials = credentials).toString()
                        } catch (e: Exception) {
                            e.localizedMessage ?: "error"
                        }
                    }
                }
                GreetingView(text = text)
            }
        }
    }
 */
