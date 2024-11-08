package com.unlam.mav.ktor.di

import com.unlam.mav.ktor.data.database.cache.AndroidDatabaseDriverFactory
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.repository.CharacterRepository
import com.unlam.mav.ktor.data.repository.CharacterRepositoryImp
import com.unlam.mav.ktor.ui.galleryscreen.GalleryScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> {
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

    single<KtorService> {
        KtorService(get())
    }

    single<CharacterRepository> {
        CharacterRepositoryImp(
            ktorService = get(),
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            )
        )
    }

    viewModel {
        GalleryScreenViewModel(
            repository = get()
        )
    }
}
