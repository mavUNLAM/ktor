package com.unlam.mav.ktor.core

class MarvelCrypto {

    fun getHash(timestamp: String, privateKey: String, publicKey: String): String {
        return ""
    }

}

expect fun getNativeHash(ts: String, privateKey: String, publicKey: String): String
