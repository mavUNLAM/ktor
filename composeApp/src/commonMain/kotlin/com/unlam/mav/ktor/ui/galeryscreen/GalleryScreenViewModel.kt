package com.unlam.mav.ktor.ui.galeryscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.network.login.LogIngCredentials
import com.unlam.mav.ktor.data.network.model.toMarvelCharacter
import com.unlam.mav.ktor.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GalleryScreenViewModel(
    private val credentials: LogIngCredentials,
    private val ktorService: KtorService
    ): ViewModel() {
    private val _state = MutableStateFlow<GalleryScreenState>(GalleryScreenState.Loading)
    val state = _state.asStateFlow()

    private var currentPage = 0
    private var currentList = emptyList<MarvelCharacter>()

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
                    page = currentPage,
                    logIngCredentials = credentials
                )
            val list: List<MarvelCharacter> = characters.map { it.toMarvelCharacter() }
            currentList = currentList + list
            _state.value = GalleryScreenState.Success(currentList)
        }
    }
}