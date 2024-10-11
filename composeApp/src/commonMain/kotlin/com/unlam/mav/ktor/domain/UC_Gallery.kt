package com.unlam.mav.ktor.domain

import com.unlam.mav.ktor.data.repository.CharacterRepository
import com.unlam.mav.ktor.data.repository.OrderBy
import com.unlam.mav.ktor.domain.model.MarvelCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UC_Gallery(
    private val repository: CharacterRepository
) {
    private var nextPage = 0

    fun getCharacters(): Flow<List<MarvelCharacter>> = flow {
        val temp = repository.getCharacters(nextPage, OrderBy.NAME_ASCENDING).flowOn(Dispatchers.IO)
        nextPage++
        temp.collect { repoList ->
            emit(
                repoList.filter { it.description.isNotBlank()}
            )
        }
    }.flowOn(Dispatchers.IO)
}
