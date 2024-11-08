package com.unlam.mav.ktor.data.repository

import com.unlam.mav.ktor.data.LOGIN_CREDENTIALS
import com.unlam.mav.ktor.data.database.cache.ExpectActualDatabase
import com.unlam.mav.ktor.data.database.entity.DelightCharacter
import com.unlam.mav.ktor.data.database.entity.toMarvelCharacter
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.network.KtorState
import com.unlam.mav.ktor.data.network.model.Character
import com.unlam.mav.ktor.data.network.model.toDelightCharacter
import com.unlam.mav.ktor.data.network.model.toMarvelCharacter

class CharacterRepositoryImp(
    private val ktorService: KtorService,
    private val database: ExpectActualDatabase
):CharacterRepository {

    private var additionalPages = 0

    override suspend fun getCharacters(page: Int): CharacterRepositoryState {
        if(page < 1) {
            return CharacterRepositoryState.Error(Exception("Page must be greater than 0"))
        }
        try {
            var tempCounter = additionalPages
            val list = database.getAllCharactersFromPage((tempCounter + page).toLong())
            return if(list.size >= 20) {
                CharacterRepositoryState.Success(list.map { it.toMarvelCharacter() })
            } else {
                val tempList = mutableListOf<Character>()
                val tempDatabaseList = mutableListOf<DelightCharacter>()
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
                            tempDatabaseList.addAll(
                                newList.map {
                                    it.toDelightCharacter(page + tempCounter)
                                }
                            )
                            if(tempList.size < 20) {
                                tempCounter++
                            }
                        }
                    }
                } while(tempList.size < 20)
                additionalPages = tempCounter
                database.insertCharacterList(tempDatabaseList)
                CharacterRepositoryState.Success(tempList.map { it.toMarvelCharacter() })
            }
        } catch (e: Exception) {
            return CharacterRepositoryState.Error(e)
        }
    }
}
