package com.tanovait.sunnyapp.ui

sealed class State {
    object PanelIsOpen : State()
    object PanelIsClosed : State()
}

val state: State = State.PanelIsClosed

fun onBackBackButtonClicked() {
    when (state) {
        State.PanelIsClosed -> {
            goOneScreenBack()
        }
        State.PanelIsOpen -> {
            closePanel()
        }
    }
}

fun onScreenResume(){
    when (state) {
        State.PanelIsClosed -> {
            //nothing
        }
        State.PanelIsOpen -> {
            closePanel()
        }
    }
}

fun onButtonClicked(){
    when (state) {
        State.PanelIsClosed -> {
             openPanel()
        }
        State.PanelIsOpen -> {
            closePanel()
        }
    }
}


fun hideCloseButton() {
    TODO("Not yet implemented")
}

fun showCloseButton() {
    TODO("Not yet implemented")
}

fun goOneScreenBack() {
    TODO("Not yet implemented")
}

fun closePanel() {
    TODO("Not yet implemented")
}

fun openPanel() {
    TODO("Not yet implemented")
}

fun main() {


    when (state) {
        State.PanelIsClosed -> {
            println("Panel is closed")
        }
        State.PanelIsOpen -> {
            println("Panel is open")
        }
    }
}