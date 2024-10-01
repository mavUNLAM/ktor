package com.unlam.mav.ktor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class Greeting {
    private val platform = getPlatform()

    private val client = HttpClient()

    suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return "Hello, ${platform.name}!" + response.bodyAsText()
    }
}
