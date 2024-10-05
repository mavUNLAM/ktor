package com.unlam.mav.ktor.ui.galeryscreen

import com.unlam.mav.ktor.domain.model.MarvelCharacter

sealed class GalleryScreenState {
    data object Loading : GalleryScreenState()
    data class Success(val characters: List<MarvelCharacter>) : GalleryScreenState()
    data class Error(val message: String) : GalleryScreenState()
}
