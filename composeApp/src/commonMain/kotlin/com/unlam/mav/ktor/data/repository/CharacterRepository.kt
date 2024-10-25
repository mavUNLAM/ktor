package com.unlam.mav.ktor.data.repository

import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(page: Int = 1, orderBy: OrderBy = OrderBy.NAME_ASCENDING): Flow<CharacterRepositoryState>
}
