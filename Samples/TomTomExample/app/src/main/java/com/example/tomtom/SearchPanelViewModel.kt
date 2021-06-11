package com.example.tomtom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SearchPanelViewModel{
    private val _text = MutableLiveData<String>().apply {
        this.postValue("Hi")
    }

    val text: LiveData<String> = _text
}