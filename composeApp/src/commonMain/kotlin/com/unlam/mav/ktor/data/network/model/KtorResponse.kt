package com.unlam.mav.ktor.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorResponse(
    @SerialName("code") val code: String,
    @SerialName("data")val data: Data,
    @SerialName("etag") val etag: String,
    @SerialName("status") val status: String
)
