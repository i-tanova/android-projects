package com.tanovait.vanlanschotkempen.composablesamples.ui.message

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tanovait.vanlanschotkempen.composablesamples.R

@Composable
fun MessageCardMutable(msg: MessageMutable, changeMe: () -> Unit) {
    Row {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
        )

        Column(modifier = Modifier.clickable {
            Log.d("MessageCardMutable", "Clicked")
            changeMe()
        }) {
            Text(text = msg.author)
            Text(text = msg.body)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
        )

        Column {
            Text(text = msg.author)
            Text(text = msg.body)
        }
    }
}