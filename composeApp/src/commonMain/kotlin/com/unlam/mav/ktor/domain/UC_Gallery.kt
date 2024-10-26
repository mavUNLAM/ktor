package com.unlam.mav.ktor.domain

import com.unlam.mav.ktor.data.repository.CharacterRepository
import com.unlam.mav.ktor.data.repository.CharacterRepositoryState
import com.unlam.mav.ktor.data.repository.OrderBy
import com.unlam.mav.ktor.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch

class UC_Gallery(
    private val repository: CharacterRepository
) {
    private var nextPage = 0
    // falta que cuando ya no haya más páginas -> mostrar todos los personajes sin descripción
    private val emptyDescriptionList = mutableListOf<MarvelCharacter>()

    private val _marvelList = MutableStateFlow<List<MarvelCharacter>>(emptyList())
    val marvelList = _marvelList.asStateFlow()

    private val _galleryState = MutableStateFlow<UC_GalleryState>(UC_GalleryState.Loading)
    val galleryState = _galleryState.asStateFlow()


    suspend fun getCharacters() {
        val temp = repository.getCharacters(nextPage, OrderBy.NAME_ASCENDING)
        temp
            .catch { _galleryState.value = UC_GalleryState.Error(it) }
            .collect { repositoryState ->
            when(repositoryState) {
                is CharacterRepositoryState.Error -> _galleryState.value = UC_GalleryState.Error(repositoryState.error)
                is CharacterRepositoryState.Loading -> _galleryState.value = UC_GalleryState.Loading
                is CharacterRepositoryState.Success -> {
                    val list = repositoryState.characters.filter {
                        it.description.isNotBlank()
                    }
                    val tempEmptyDescriptionList = repositoryState.characters.filter {
                        it.description.isBlank()
                    }
                    emptyDescriptionList.addAll(tempEmptyDescriptionList)
                    val tempMarvelList = mutableListOf<MarvelCharacter>()
                    tempMarvelList.addAll(_marvelList.value)
                    tempMarvelList.addAll(list)
                    _marvelList.value = tempMarvelList
                    nextPage++
                    _galleryState.value = UC_GalleryState.Success
                }
            }
        }
    }
}
