package com.unlam.mav.ktor.data.repository

import com.unlam.mav.ktor.data.network.KtorOrderBy
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.network.login.LogingCredentials
import com.unlam.mav.ktor.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImp(private val ktorService: KtorService):CharacterRepository {
    override fun getCharacters(page: Int, orderBy: OrderBy): Flow<List<MarvelCharacter>> = flow {
        val orderByKtor: KtorOrderBy = when(orderBy){
            OrderBy.NAME_ASCENDING -> KtorOrderBy.NAME_ASCENDING
            OrderBy.NAME_DESCENDING -> KtorOrderBy.NAME_DESCENDING
            OrderBy.ID_ASCENDING -> TODO()
            OrderBy.ID_DESCENDING -> TODO()
        }
        val response = ktorService
            .getCharactersFlow(
                page = page,
                orderBy =  orderByKtor,
                logIngCredentials = LogingCredentials("","")
            )


    }
}
