package com.unlam.mav.ktor.data.repository

import com.unlam.mav.ktor.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(page: Int, orderBy: OrderBy): Flow<List<MarvelCharacter>>
}
