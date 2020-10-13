package nl.virtualaffairs.bankingright.redux.store

import com.example.myapplication.redux.redux.store.Action
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

abstract class Subscriber<in T>(private val executeOnThisThread: Executor? = null) {

    fun onNext(data: T) {
        executeOnThisThread?.execute { handle(data) } ?: handle(data)
    }

    abstract fun handle(data: T)
}

abstract class SideEffect(executeOnThisThread: ExecutorService? = null) : Subscriber<Action>(executeOnThisThread)

abstract class StateHandler(executeOnThisThread: Executor? = null) : Subscriber<State>(executeOnThisThread)


/**
 *
 *  Extension function to CopyOnWriteArrayList that will process action among all items in the array
 *
 */
fun CopyOnWriteArrayList<SideEffect>.dispatch(action: Action) {
    forEach { it.onNext(action) }
}

fun CopyOnWriteArrayList<StateHandler>.dispatch(state: State) {
    forEach { it.onNext(state) }
}