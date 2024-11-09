package com.unlam.mav.ktor.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDataContainer(
    @SerialName("count") val count: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("results") val results: List<Character>,
    @SerialName("total") val total: Int
)
