package com.example.test

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirstFragmentViewModel: ViewModel() {

    val details: Flow<UserDetails> = flow {
        UserDetails()
    }
}

data class UserDetails(val name: String ="John")