package com.unlam.mav.ktor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.unlam.mav.ktor.data.database.cache.DatabaseDriverFactory
import com.unlam.mav.ktor.data.database.cache.ExpectActualDatabase
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.repository.CharacterRepositoryImp
import com.unlam.mav.ktor.ui.galleryscreen.GalleryScreen
import com.unlam.mav.ktor.ui.galleryscreen.GalleryScreenViewModel
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Composable
fun App(databaseDriverFactory: DatabaseDriverFactory, initLogger: () -> Unit) {
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
                install(HttpRequestRetry) {
                    retryOnServerErrors(maxRetries = 5)
                    exponentialDelay()
                }
                // usar naper como logger
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object: Logger {
                        override fun log(message: String) {
                            Napier.v(tag = "HttpClient", message = message)
                        }
                    }
                }
            }.also { initLogger() }
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
