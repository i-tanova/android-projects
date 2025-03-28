package com.tanovait.vanlanschotkempen.composablesamples.ui.message

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

const val TAG = "MessageViewModel"

class MessageViewModel : ViewModel() {

    companion object {
        const val TAG = "MessageViewModel"
    }

    var complexState by mutableStateOf(MessageMutable("Author", "Body"))

    fun changeMe(){
        // FixMe why this doesn't work:
        //  complexState = complexState.author = "changed"
        // mutable state need the object to be changed in order to trigger re-composition!!
        // using immutable objects will save us from the temptation to change values directly
        complexState = complexState.copy(author = "changed")
    }

}

data class MessageMutable(var author: String, val body: String)
data class Message(val author: String, val body: String)