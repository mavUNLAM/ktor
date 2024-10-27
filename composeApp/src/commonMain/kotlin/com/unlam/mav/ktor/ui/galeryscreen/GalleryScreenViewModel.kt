package com.unlam.mav.ktor.ui.galeryscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.network.model.toMarvelCharacter
import com.unlam.mav.ktor.domain.UC_Gallery
import com.unlam.mav.ktor.domain.model.MarvelCharacter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GalleryScreenViewModel(
    private val ktorService: KtorService,
    private val useCaseGallery: UC_Gallery,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): ViewModel() {
    private val _state = MutableStateFlow<GalleryScreenState>(GalleryScreenState.Loading)
    val state = _state.asStateFlow()

    private var currentPage = 0
    private val currentList = mutableListOf<MarvelCharacter>()

    val marvelList = useCaseGallery
        .marvelList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    init {
        loadCharacters()
    }

    /**
     * Loads the first page of characters.
     *
     * It lacks try catch.
     *
     * Tiene un problema: va a pedir la siguiente página aunque no haya más.
     */
    fun loadCharacters() {
        viewModelScope.launch {
            currentPage++
            val characters = ktorService
                .getCharacters(
                    page = currentPage
                    //,logIngCredentials = credentials. cambiarlo en un futuro a una implementación de login
                )
            val list: List<MarvelCharacter> = characters.map { it.toMarvelCharacter() }
            currentList.addAll(list)
            _state.value = GalleryScreenState.Success(currentList)
        }
    }

    fun loadCharactersV2() {
        viewModelScope.launch(ioDispatcher) {

        }
    }
}
