package nl.virtualaffairs.bankingright.redux.reducer

import com.example.myapplication.redux.redux.store.Action
import nl.virtualaffairs.bankingright.redux.store.RegistrationState

object RegistrationReducer : Reducer<Action, RegistrationState>(){

    override fun reduce(action: Action, currentState: RegistrationState): RegistrationState {
        return RegistrationState.Idle
    }

}