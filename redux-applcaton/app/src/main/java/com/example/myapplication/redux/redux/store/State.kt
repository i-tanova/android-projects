package nl.virtualaffairs.bankingright.redux.store

// interface ViewSate

//interface AppState

//data class State(val viewState: ViewSate)

//data class SplashViewState(val showStartButton:Boolean) : ViewSate

//object IdleViewState : ViewSate

sealed class State

sealed class RegistrationState: State(){
    object Idle: RegistrationState()
}