package com.unlam.mav.ktor.data.repository

import com.unlam.mav.ktor.data.LOGIN_CREDENTIALS
import com.unlam.mav.ktor.data.network.KtorOrderBy
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.network.KtorState
import com.unlam.mav.ktor.data.network.model.toMarvelCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepositoryImp(private val ktorService: KtorService):CharacterRepository {

    override fun getCharacters(page: Int, orderBy: OrderBy): Flow<CharacterRepositoryState> = flow {
        emit(CharacterRepositoryState.Loading)
        if(page < 1) {
            emit(CharacterRepositoryState.Error(Exception("Page must be greater than 0")))
            return@flow
        }
        val orderByKtor: KtorOrderBy = when(orderBy){
            OrderBy.NAME_ASCENDING -> KtorOrderBy.NAME_ASCENDING
            OrderBy.NAME_DESCENDING -> KtorOrderBy.NAME_DESCENDING
            OrderBy.ID_ASCENDING -> TODO()
            OrderBy.ID_DESCENDING -> TODO()
        }
        ktorService
            .getCharactersFlow(
                page = page,
                orderBy =  orderByKtor,
                logIngCredentials = LOGIN_CREDENTIALS
            ).catch { emit(CharacterRepositoryState.Error(it)) } // hay que especificar más el error
            .collect { ktorState ->
                when(ktorState){
                    is KtorState.Error -> CharacterRepositoryState.Error(ktorState.error) // lo mismo acá
                    is KtorState.Loading -> emit(CharacterRepositoryState.Loading)
                    is KtorState.Success -> {
                        val tempList = ktorState.characters.map { it.toMarvelCharacter() }
                        emit(CharacterRepositoryState.Success(tempList))
                    }
                }
            }
    }.flowOn(Dispatchers.IO)
}
