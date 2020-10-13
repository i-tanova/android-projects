package nl.virtualaffairs.bankingright.redux.store

//sealed class ApplicationState(val authenticationManagerState: AuthenticationManagerState) : State {
//    object UserNotRegistered : ApplicationState(authenticationManagerState = AuthenticationManagerState())
//    object UserRegistered : ApplicationState(authenticationManagerState = AuthenticationManagerState())
//}
//
//class SessionRepository {
//
//}
//
//class ApplicationCreated : Action
//
//interface Reducer<S : State> {
//    fun dispatch(action: Action, registrationStore:ApplicationStore, dispatchF: KFunction2<ApplicationStore, @ParameterName(name = "action") Action, Unit>): S
//}
//
//class ApplicationStore(val initialState: ApplicationState, val registrationRepository: RegistrationRepository, val sessionRepository: SessionRepository, private val reducersMap: Map<ApplicationState, Reducer<ApplicationState>>) : RegistrationStore<ApplicationState> {
//
//    var applicationState: ApplicationState = initialState
//
//    override fun dispatch(action: Action) {
//        when (action) {
//            is ApplicationCreated -> {
//                if (registrationRepository.isUserRegistered()) {
//                    applicationState = ApplicationState.UserRegistered
//                } else {
//                    applicationState = ApplicationState.UserNotRegistered
//                }
//
//                //Listen for the session
//            }
//        }
//
//        //var dispatchF: Function1<Unit, Action> = ApplicationStore::dispatch(action)
//
//        applicationState = reducersMap.get(applicationState)!!.dispatch(action, this, ApplicationStore::dispatch)
//    }
//
//    override fun add(subscriber: StoreSubscriber<ApplicationState>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun remove(subscriber: StoreSubscriber<ApplicationState>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}