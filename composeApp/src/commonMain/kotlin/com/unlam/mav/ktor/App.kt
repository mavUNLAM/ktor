package com.unlam.mav.ktor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.unlam.mav.ktor.data.network.KtorService
import com.unlam.mav.ktor.data.network.login.LogIn
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val scope = rememberCoroutineScope()
                var text by remember { mutableStateOf("Loading") }
                LaunchedEffect(true) {
                    scope.launch {
                        val ktorService = KtorService()
                        val credentials = LogIn().logInNow("user", "password")

                        text = try {
                            //Greeting().greeting()
                            ktorService.getCharacters(page = 1, logIngCredentials = credentials).toString()
                        } catch (e: Exception) {
                            e.localizedMessage ?: "error"
                        }
                    }
                }
                GreetingView(text = text)
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = text
    )
}
