package com.unlam.mav.ktor.data

import com.unlam.mav.ktor.data.network.login.LogingCredentials

internal val PUBLIC_KEY = "public_key"
internal val PRIVATE_KEY = "private_key"
internal val LOGIN_CREDENTIALS = LogingCredentials(
    publicKey = PUBLIC_KEY,
    privateKey = PRIVATE_KEY
)
