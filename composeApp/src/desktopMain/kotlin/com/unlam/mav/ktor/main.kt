package com.unlam.mav.ktor

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ktor",
    ) {
        //ya he dejado por mucho tiempo tirada la versión desktop.... así que por ahora lo dejamos así
        //App()
    }
}
