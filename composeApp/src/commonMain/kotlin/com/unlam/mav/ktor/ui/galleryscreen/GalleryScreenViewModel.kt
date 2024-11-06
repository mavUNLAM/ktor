package com.unlam.mav.ktor.ui.galleryscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.mav.ktor.data.repository.CharacterRepository
import com.unlam.mav.ktor.data.repository.CharacterRepositoryState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryScreenViewModel(
    private val repository: CharacterRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): ViewModel() {
    private val _state = MutableStateFlow<GalleryScreenState>(GalleryScreenState.Loading)
    val state = _state.asStateFlow()

    private var currentPage = 0

    init {
        loadCharactersV2()
    }

    /**
     * Loads the first page of characters.
     *
     * It lacks try catch.
     *
     * Tiene un problema: va a pedir la siguiente página aunque no haya más.
     */
    fun loadCharactersV2() {
        viewModelScope.launch(ioDispatcher) {
            currentPage++
            when (val repositoryResult = repository.getCharacters(currentPage)) {
                is CharacterRepositoryState.Error -> {
                    val error = repositoryResult.error
                    _state.update {
                        GalleryScreenState.Error(error.toString())
                    }
                }
                is CharacterRepositoryState.Success -> {
                    val list = repositoryResult.characters

                    if(currentPage == 1) {
                        _state.update {
                            GalleryScreenState.Success(list)
                        }
                    } else {
                        _state.update {
                            val oldState = it as GalleryScreenState.Success
                            GalleryScreenState.Success(oldState.characters + list)
                        }
                    }
                }
            }
        }
    }
}
