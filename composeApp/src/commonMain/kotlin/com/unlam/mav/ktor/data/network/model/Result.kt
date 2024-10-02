package com.unlam.mav.ktor.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("description") val description: String,
    @SerialName("id") val id: String,
    @SerialName("modified") val modified: String,
    @SerialName("name") val name: String,
    @SerialName("thumbnail") val thumbnail: Thumbnail,
)
