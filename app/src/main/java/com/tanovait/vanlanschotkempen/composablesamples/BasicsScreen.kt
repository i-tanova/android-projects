package com.tanovait.vanlanschotkempen.composablesamples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tanovait.vanlanschotkempen.composablesamples.ui.theme.ComposableSamplesTheme

@Composable
fun BasicsScreen() {
   TestCard()
}

@Composable
fun TestCard() {
    //The Column function lets you arrange elements vertically.
    Column {
        Column {
            Text(text = "Column 1")
            Text(text = "Column 2")
        }
        //You can use Row to arrange items horizontally
        Row {
            Text(text = "Row 1")
            Text(text = "Row 2")
        }

        // Box on top of each other (stack elements)
        Box(modifier = Modifier){
            Text(text = "Box 1")
            Text(text = "Box 2", color = Color.Red)
        }
    }
}

@Preview
@Composable
fun PreviewTest() {
    ComposableSamplesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BasicsScreen()
        }
    }
}