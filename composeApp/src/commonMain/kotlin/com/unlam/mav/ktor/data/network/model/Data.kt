package com.unlam.mav.ktor.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("count") val count: String,
    @SerialName("limit") val limit: String,
    @SerialName("offset") val offset: String,
    @SerialName("results") val results: List<Result>,
    @SerialName("total") val total: String
)
