package com.unlam.mav.ktor.data.database.entity

import com.unlam.mav.ktor.domain.model.MarvelCharacter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DelightCharacter(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String?,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("page") val page: Int
)

fun DelightCharacter.toMarvelCharacter() = MarvelCharacter(
    id = this.id,
    name = this.name,
    description = if(this.description.isNullOrBlank()) {
        "empty"
    } else {
        this.description
    },
    thumbnail = this.thumbnail
)
