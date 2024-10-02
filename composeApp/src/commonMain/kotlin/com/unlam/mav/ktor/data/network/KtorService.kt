package com.unlam.mav.ktor.data.network

import com.unlam.mav.ktor.core.MarvelCrypto
import com.unlam.mav.ktor.data.network.login.LogIngCredentials
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

class KtorService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun getCharacter(id: Int, logIngCredentials: LogIngCredentials): String {
        val timestamp = Clock.System.now().toEpochMilliseconds()
        val hash = MarvelCrypto()
            .getHash(
                timestamp.toString() +
                        logIngCredentials.privateKey +
                        logIngCredentials.publicKey
            )

        return ""
    }
}
