package nl.virtualaffairs.bankingright.redux.reducer


import com.example.myapplication.redux.redux.store.Action
import nl.virtualaffairs.bankingright.redux.store.State

abstract class Reducer<in A : Action, in S: State> {

   open fun reduce(action: A, currentState: S): State = currentState

}