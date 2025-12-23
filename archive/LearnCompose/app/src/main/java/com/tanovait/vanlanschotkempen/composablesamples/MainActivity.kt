package com.tanovait.vanlanschotkempen.composablesamples

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tanovait.vanlanschotkempen.composablesamples.ui.theme.ComposableSamplesTheme
import com.tanovait.vanlanschotkempen.officalAndroidTraining.OfficalTrainingActivity

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposableSamplesTheme {
                ComposableSampleApp(onGoToOfficialTraining = ::startOfficialTraining)
            }
        }
    }

    @Composable
    private fun ComposableSampleApp(onGoToOfficialTraining: () -> Unit = {}) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                ElevatedButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        onGoToOfficialTraining.invoke()
                    },
                ) {
                    Text("Go to official android Training")
                }

//                Spacer(modifier = Modifier.weight(1f))
//
//                val viewModel: MessageViewModel by viewModels()
//                MessageCardMutable(viewModel.complexState, viewModel::changeMe)
            }
        }
    }

    private fun startOfficialTraining() {
        val intent = Intent(this, OfficalTrainingActivity::class.java)
        startActivity(intent)
    }
}
