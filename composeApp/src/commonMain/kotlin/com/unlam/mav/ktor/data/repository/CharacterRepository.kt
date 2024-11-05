package com.unlam.mav.ktor.data.repository


interface CharacterRepository {
    suspend fun getCharacters(
        page: Int = 1
    ): CharacterRepositoryState
}
