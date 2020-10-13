package com.example.myapplication.redux.redux.store

sealed class Action


sealed class ApplicationAction: Action(){
    object ApplicationCreated: ApplicationAction()
}

sealed class SplashAction: Action(){
    object Init : SplashAction()
    object StartButtonClick : SplashAction()
}

sealed class NavigationAction: Action(){

}

internal interface ActionHandler<in T : Action> {
    fun handle(action: T, actionDispatcher: (Action) -> Unit)
}