package com.unlam.mav.ktor.data.repository

import com.unlam.mav.ktor.data.LOGIN_CREDENTIALS
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.network.KtorState
import com.unlam.mav.ktor.data.network.model.Character
import com.unlam.mav.ktor.data.network.model.toMarvelCharacter

class CharacterRepositoryImp(
    private val ktorService: KtorService
):CharacterRepository {

    private var aditionalPages = 0

    override suspend fun getCharacters(page: Int): CharacterRepositoryState {
        if(page < 1) {
            return CharacterRepositoryState.Error(Exception("Page must be greater than 0"))
        }
        try {
            val tempList = mutableListOf<Character>()
            var tempCounter = aditionalPages
            do {
                val ktorResponse = ktorService
                    .getCharacters(
                        page = page + tempCounter,
                        logIngCredentials = LOGIN_CREDENTIALS //internal val LOGIN_CREDENTIALS = (publicKey, privateKey)
                    ) // hay que especificar más el error
                when(ktorResponse){
                    is KtorState.Error -> return CharacterRepositoryState.Error(ktorResponse.error) // lo mismo acá
                    is KtorState.Success -> {
                        val newList = ktorResponse
                            .characters
                            .filter {
                                it.description.isNotBlank()
                            }
                        tempList.addAll(newList)
                        if(tempList.size < 20) {
                            tempCounter++
                        }
                    }
                }
            } while(tempList.size < 20)
            aditionalPages = tempCounter
            return CharacterRepositoryState.Success(tempList.map { it.toMarvelCharacter() })
        } catch (e: Exception) {
            return CharacterRepositoryState.Error(e)
        }
    }
}
