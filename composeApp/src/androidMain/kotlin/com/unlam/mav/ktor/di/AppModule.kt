package com.unlam.mav.ktor.di

/*
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

 */
