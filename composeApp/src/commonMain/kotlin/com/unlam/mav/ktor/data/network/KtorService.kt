package com.unlam.mav.ktor.data.network

import com.unlam.mav.ktor.core.MarvelCrypto
import com.unlam.mav.ktor.data.LOGIN_CREDENTIALS
import com.unlam.mav.ktor.data.network.login.LogingCredentials
import com.unlam.mav.ktor.data.network.model.Character
import com.unlam.mav.ktor.data.network.model.KtorResponse
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
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

class KtorService {
    // MarvelCrypto y HttpClient tienen que ser parámetros de la clase
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
        private const val LIMIT = "20"
        private const val CHARACTERS_BASE_URL = "https://gateway.marvel.com/v1/public/characters"
    }

    suspend fun getCharacters(page: Int, logIngCredentials: LogingCredentials = LOGIN_CREDENTIALS): List<Character> {
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

    fun getCharactersFlow(
        page: Int,
        logIngCredentials: LogingCredentials = LOGIN_CREDENTIALS,
        orderBy: KtorOrderBy
    ): Flow<KtorState> = flow {
        emit(KtorState.Loading)
        val offset = (page - 1) * LIMIT.toInt()
        val timestamp = Clock.System.now().toEpochMilliseconds()
        // la dependencia de marvelCrypto se tiene que pedir por parámetro de la clase.
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
            parameter("orderBy", orderBy.parameter)
            parameter("limit", LIMIT)
            parameter("offset", offset)
        }
        if(response.status == HttpStatusCode.OK){
            emit(KtorState.Success(response.body<KtorResponse>().data.results))
        }else{
            //tengo que crear KtorException y usarlo en vez de Exception
            emit(KtorState.Error(Exception("Network Error ${response.status.value} in KtorService")))
        }
    }.flowOn(Dispatchers.IO)
}
