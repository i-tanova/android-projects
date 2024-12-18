package com.tanovait.vanlanschotkempen.composablesamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.tanovait.vanlanschotkempen.composablesamples.ui.message.MessageCardMutable
import com.tanovait.vanlanschotkempen.composablesamples.ui.message.MessageViewModel
import com.tanovait.vanlanschotkempen.composablesamples.ui.theme.ComposableSamplesTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposableSamplesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MessageViewModel by viewModels()
                    MessageCardMutable(viewModel.complexState, viewModel::changeMe)
                }
            }
        }
    }
}
