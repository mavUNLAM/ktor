package com.unlam.mav.ktor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform