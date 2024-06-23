package tanovait.constilations

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingsList(greetings = listOf("Hello", "Hola", "Bonjour", "Ciao", "你好", "こんにちは"))
        }
    }
}

@Composable
fun GreetingsList(greetings: List<String>) {
    LazyColumn {
        items(greetings) { greeting ->
            GreetingItem(name = greeting)
        }
    }
}

@Composable
fun GreetingItem(name: String) {
    Text(text = name)
}

@Preview
@Composable
fun PreviewGreetingsList() {
    GreetingsList(greetings = listOf("Hello", "Hola", "Bonjour", "Ciao", "你好", "こんにちは"))
}