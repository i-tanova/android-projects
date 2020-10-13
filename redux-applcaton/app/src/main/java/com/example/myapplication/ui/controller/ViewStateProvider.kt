package com.example.myapplication.ui.controller

import ViewState
import nl.virtualaffairs.bankingright.redux.store.State

interface ViewStateProvider<S : State, V : ViewState> {
    fun provideViewState(state: S): V
}
