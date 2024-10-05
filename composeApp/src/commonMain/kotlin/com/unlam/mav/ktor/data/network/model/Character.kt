package com.unlam.mav.ktor.data.network.model

import com.unlam.mav.ktor.domain.model.MarvelCharacter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    @SerialName("description") val description: String,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("thumbnail") val thumbnail: Thumbnail,
)

fun Character.toMarvelCharacter() = MarvelCharacter(
    id = id,
    name = name,
    description = description,
    thumbnail = "${thumbnail.path}.${thumbnail.extension}"
)
