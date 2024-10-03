package com.unlam.mav.ktor.data.network

import com.unlam.mav.ktor.core.MarvelCrypto
import com.unlam.mav.ktor.data.network.login.LogIngCredentials
import com.unlam.mav.ktor.data.network.model.KtorResponse
import com.unlam.mav.ktor.data.network.model.Character
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
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
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
    companion object {
        private const val LIMIT = "100"
        private const val CHARACTERS_BASE_URL = "https://gateway.marvel.com/v1/public/characters"
    }

    suspend fun getCharacters(page: Int, logIngCredentials: LogIngCredentials): List<Character> {
        val offset = (page - 1) * LIMIT.toInt()
        val timestamp = Clock.System.now().toEpochMilliseconds()
        val hash = MarvelCrypto()
            .getHash(
                timestamp.toString() +
                        logIngCredentials.privateKey +
                        logIngCredentials.publicKey
            )
        val response = client.get(CHARACTERS_BASE_URL) {
            contentType(ContentType.Application.Json)
            parameter("ts", timestamp.toString())
            parameter("apikey", logIngCredentials.publicKey)
            parameter("hash", hash)
            parameter("orderBy", "name")
            parameter("limit", LIMIT)
            parameter("offset", offset)
        }
        return response.body<KtorResponse>().data.results
    }


}
