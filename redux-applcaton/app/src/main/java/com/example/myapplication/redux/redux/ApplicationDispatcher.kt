package com.example.myapplication.redux.redux

import com.example.myapplication.redux.redux.store.Action
import com.example.myapplication.redux.redux.store.ApplicationAction
import nl.virtualaffairs.bankingright.redux.store.RegistrationStore
import nl.virtualaffairs.bankingright.redux.store.State
import nl.virtualaffairs.bankingright.redux.store.StateHandler
import nl.virtualaffairs.bankingright.redux.store.TimberLogger

class RegistrationRepository {

    fun isUserRegistered(): Boolean {
        return false
    }
}

object ApplicationDispatcher {

    val registrationRepository = RegistrationRepository()
    var registrationStore = RegistrationStore(null, TimberLogger())

    val listChangeListener: MutableList<ChangeListener> = mutableListOf()

    var isUserRegistered = false

    fun dispatch(action: Action) {
        when (action) {
            ApplicationAction.ApplicationCreated -> {
                isUserRegistered = !registrationRepository.isUserRegistered()

                if(isUserRegistered){
                    registrationStore.stateHandlers.add(object : StateHandler() {
                        override fun handle(data: State) {
                            notifyForStateChange()
                        }
                    })
                }
            }
        }

        if(isUserRegistered){
            registrationStore.dispatchAction(action)
        }
    }

    private fun notifyForStateChange() {
        listChangeListener.forEach{it.onStateChanged()}
    }

    fun addChangeListener(listener: ChangeListener){
        listChangeListener.add(listener)
    }

    fun removeChangeListener(listener: ChangeListener){
        listChangeListener.remove(listener)
    }

}


interface ChangeListener{
    fun onStateChanged()
}