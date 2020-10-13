package com.example.myapplication.redux.redux.store

import nl.virtualaffairs.bankingright.redux.store.SideEffect
import nl.virtualaffairs.bankingright.redux.store.State
import nl.virtualaffairs.bankingright.redux.store.StateHandler
import java.util.concurrent.CopyOnWriteArrayList

interface IStore <S: State> {
    /**
     *
     * An array that will hold all Side effects. Use SideEffect to react to an action without returning State
     *
     */
    val sideEffects: CopyOnWriteArrayList<SideEffect>

    /**
     *
     * Use StateHandler to receive event when state is changed
     *
     */
    val stateHandlers: CopyOnWriteArrayList<StateHandler>

    fun dispatchAction(action: Action)
    fun dispatchState(state: S)
}