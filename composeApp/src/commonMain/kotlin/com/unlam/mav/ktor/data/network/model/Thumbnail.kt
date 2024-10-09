package com.unlam.mav.ktor.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(
    @SerialName("extension")val extension: String,
    @SerialName("path")val path: String
)

fun Thumbnail.toUrl(): String = "$path.$extension"
