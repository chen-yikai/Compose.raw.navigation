package com.example.composerawnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composerawnavigation.ui.theme.ComposerawnavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val modal = ViewModelProvider(this)[NavViewModal::class.java]
        setContent {
            ComposerawnavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            when (modal.currentScreen) {
                                Screen.First.name -> FirstScreen(modal)
                                Screen.Second.name -> SecondScreen(modal)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FirstScreen(modal: NavViewModal) {
    Text("First Screen")
    FilledTonalButton(onClick = {
        modal.navTo(Screen.Second)
    }) { Text("Nav to Second Screen") }
}

@Composable
fun SecondScreen(modal: NavViewModal) {
    Text("Second Screen")
    FilledTonalButton(onClick = {
        modal.pop()
    }) { Text("Back") }
}

class NavViewModal : ViewModel() {
    private val stack = mutableStateListOf<Screen>()
    var currentScreen: String by mutableStateOf(Screen.First.name)
        private set

    init {
        stack.add(Screen.First)
    }

    fun navTo(screen: Screen) {
        if (currentScreen != screen.name) {
            stack.add(screen)
            currentScreen = screen.name
        }
    }

    fun pop() {
        if (stack.size > 1) {
            stack.removeLast()
            currentScreen = stack.last().name
        }
    }
}

enum class Screen {
    First, Second
}