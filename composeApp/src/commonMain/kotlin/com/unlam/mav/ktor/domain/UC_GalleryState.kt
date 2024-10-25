package com.unlam.mav.ktor.domain

sealed class UC_GalleryState {
    data object Loading : UC_GalleryState()
    data object Success : UC_GalleryState()
    data class Error(val error: Throwable? = null): UC_GalleryState()
}
