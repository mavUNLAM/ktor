package com.unlam.mav.ktor.data.repository

import com.unlam.mav.ktor.data.LOGIN_CREDENTIALS
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.network.KtorState
import com.unlam.mav.ktor.data.network.model.toMarvelCharacter

class CharacterRepositoryImp(
    private val ktorService: KtorService
    ):CharacterRepository {

    override suspend fun getCharacters(page: Int): CharacterRepositoryState {
        if(page < 1) {
            return CharacterRepositoryState.Error(Exception("Page must be greater than 0"))
        }
        try {
            val ktorResponse = ktorService
                .getCharacters(
                    page = page,
                    logIngCredentials = LOGIN_CREDENTIALS //internal val LOGIN_CREDENTIALS = (publicKey, privateKey)
                ) // hay que especificar más el error
                    return when(ktorResponse){
                        is KtorState.Error -> CharacterRepositoryState.Error(ktorResponse.error) // lo mismo acá
                        is KtorState.Success -> {
                            val tempList = ktorResponse.characters.map { it.toMarvelCharacter() }
                            CharacterRepositoryState.Success(tempList)
                        }
                    }
        } catch (e: Exception) {
            return CharacterRepositoryState.Error(e)
        }
    }
}
