package com.unlam.mav.ktor.data

import io.ktor.client.HttpClient
import kotlinx.datetime.Clock

class KtorService {
    private val client = HttpClient()

    suspend fun getCharacter(id: Int): String {
        val timestamp = Clock.System.now().toEpochMilliseconds()

        return ""
    }
}