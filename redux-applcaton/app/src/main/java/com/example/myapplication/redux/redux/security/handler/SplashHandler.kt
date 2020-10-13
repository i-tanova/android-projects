package nl.virtualaffairs.bankingright.redux.security.handler

import com.example.myapplication.redux.redux.store.Action
import com.example.myapplication.redux.redux.store.ActionHandler
import com.example.myapplication.redux.redux.store.SplashAction

object SplashHandler: ActionHandler<SplashAction> {

    override fun handle(action: SplashAction, actionDispatcher: (Action) -> Unit) {
        //is FetchItemsAction -> fetchAllItems(actionDispatcher)
    }

//    private fun fetchAllItems(actionDispatcher: (Action) -> Unit) {
//        val db = Realm.getDefaultInstance()
//        val persistenceItems = db.queryAllItemsSortedByPosition()
//        val storeItems = persistenceItems.toStoreItemsList()
//        db.close()
//
//        actionDispatcher.invoke(ItemsLoadedAction(storeItems))
//    }

}