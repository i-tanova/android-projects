package tanovait.constilations

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.tanovait.telephony.ui.theme.AppTheme
import java.lang.reflect.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingsList(
                        greetings = listOf(
                            "Hello",
                            "Hola",
                            "Bonjour",
                            "Ciao",
                            "你好",
                            "こんにちは"
                        )
                    )
                }
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
        AppTheme {
            Surface(
                modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                GreetingsList(
                    greetings = listOf(
                        "Hello",
                        "Hola",
                        "Bonjour",
                        "Ciao",
                        "你好",
                        "こんにちは"
                    )
                )
            }
        }
    }
}