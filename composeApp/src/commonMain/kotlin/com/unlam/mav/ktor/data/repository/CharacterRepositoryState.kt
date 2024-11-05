package com.unlam.mav.ktor.data.repository

import com.unlam.mav.ktor.domain.model.MarvelCharacter

sealed class CharacterRepositoryState {
    data class Success(val characters: List<MarvelCharacter>): CharacterRepositoryState()
    data class Error(val error: Throwable? = null): CharacterRepositoryState()
}
