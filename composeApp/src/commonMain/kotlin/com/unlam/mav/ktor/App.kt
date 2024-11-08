package com.unlam.mav.ktor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.repository.CharacterRepositoryImp
import com.unlam.mav.ktor.ui.galleryscreen.GalleryScreen
import com.unlam.mav.ktor.ui.galleryscreen.GalleryScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.unlam.mav.ktor.data.database.cache.DatabaseDriverFactory
import com.unlam.mav.ktor.data.database.cache.ExpectActualDatabase

@Composable
@Preview
fun App(databaseDriverFactory: DatabaseDriverFactory) {
    MaterialTheme {
        val httpClient = remember {
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }
                // usar naper como logger
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            }
        }
        val ktorService = remember {
            KtorService(
                httpClient = httpClient
            )
        }
        val database = remember {
            ExpectActualDatabase(databaseDriverFactory)
        }
        val repository = remember {
            CharacterRepositoryImp(ktorService, database)
        }
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
