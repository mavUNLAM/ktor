package com.unlam.mav.ktor.data.network.login

class LogIn {
    fun logInNow(user: String, password: String): LogIngCredentials {
        return LogIngCredentials(
            privateKey = "f86b2a5a3902e22678b7c18bafa08e07cf5f8b1e",
            publicKey = "cfaf2296a6753090bb980a11945d9ec7")
    }
}
