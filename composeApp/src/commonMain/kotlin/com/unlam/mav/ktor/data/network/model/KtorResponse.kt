package com.unlam.mav.ktor.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorResponse(
    @SerialName("code") val code: Int,
    @SerialName("data") val data: CharacterDataContainer,
    @SerialName("etag") val etag: String,
    @SerialName("status") val status: String
)
