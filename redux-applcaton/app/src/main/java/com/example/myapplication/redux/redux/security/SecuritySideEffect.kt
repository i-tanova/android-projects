package nl.virtualaffairs.bankingright.redux.security

import nl.virtualaffairs.bankingright.redux.security.handler.SplashHandler
import com.example.myapplication.redux.redux.store.Action
import com.example.myapplication.redux.redux.store.IStore
import nl.virtualaffairs.bankingright.redux.store.SideEffect
import com.example.myapplication.redux.redux.store.SplashAction
import nl.virtualaffairs.bankingright.redux.store.RegistrationStore
import java.util.concurrent.ExecutorService

//class SecuritySideEffect(val store: IStore, executorService: ExecutorService): SideEffect(executorService){
//
//    override fun handle(action: Action) {
//        when (action) {
//            is SplashAction -> SplashHandler.handle(action) { store.dispatch(it) }
//        }
//    }
//}