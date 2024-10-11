package com.unlam.mav.ktor.data.network

import com.unlam.mav.ktor.data.network.model.Character

sealed class KtorState {
    data object Loading: KtorState()
    data class Success(val characters: List<Character>): KtorState()
    data class Error(val error: Throwable? = null): KtorState()
}
