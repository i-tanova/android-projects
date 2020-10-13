package nl.virtualaffairs.bankingright.redux.store

import com.example.myapplication.redux.redux.store.Action
import com.example.myapplication.redux.redux.store.IStore
import nl.virtualaffairs.bankingright.redux.reducer.RegistrationReducer
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue

//object RegistrationStore : RegistrationStore(
//        storeThread = Executors.newSingleThreadExecutor()) {
//
//    fun enableSecurity() {
//        SecuritySideEffect(registrationStore = this, executorService = Executors.newSingleThreadExecutor())
//    }
//}

//override val sideEffects: CopyOnWriteArrayList<SideEffect> = CopyOnWriteArrayList(),
//override val stateHandlers: CopyOnWriteArrayList<StateHandler> = CopyOnWriteArrayList(),

class RegistrationStore(
    private val storeThread: ExecutorService? = null,
    private val logger: Logger = TimberLogger()
) :
    IStore<RegistrationState> {

    private var actions = LinkedBlockingQueue<Action>()

    var state: RegistrationState = RegistrationState.Idle
        protected set

    @Synchronized
    override fun dispatchAction(action: Action) {
        actions.offer(action)
        when {
            storeThread != null -> storeThread.execute { handle(actions.poll()) }
            else -> handle(actions.poll())
        }
    }

    private fun handle(action: Action) {
        val newState: RegistrationState = reduce(action, state)
        dispatchState(newState)
        sideEffects.dispatch(action)
    }

    private fun reduce(action: Action, currentState: RegistrationState): RegistrationState {
        logger.log("action", action.toString())
        val newState = when (action) {
            else -> RegistrationReducer.reduce(action, currentState)
        }
        logger.log("new state", newState.toString())
        return newState
    }

    override fun dispatchState(state: RegistrationState) {
        this.state = state
        stateHandlers.dispatch(state)
    }

//    fun enableSecurity() {
//        SecuritySideEffect(
//            registrationStore = this,
//            executorService = Executors.newSingleThreadExecutor()
//        )
//    }

    override val sideEffects: CopyOnWriteArrayList<SideEffect> = CopyOnWriteArrayList()
    override val stateHandlers: CopyOnWriteArrayList<StateHandler> = CopyOnWriteArrayList()
}