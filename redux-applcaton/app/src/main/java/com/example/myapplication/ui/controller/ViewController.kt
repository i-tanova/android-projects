import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.myapplication.redux.redux.ApplicationDispatcher
import com.example.myapplication.redux.redux.ChangeListener
import java.util.concurrent.Executor

interface ViewState

abstract class ViewContorller<V :ViewState>
    : LifecycleObserver, ChangeListener {

    constructor(mainThread: Executor? = null)

    var isActivityRunning = false

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun addStateListener() {
        isActivityRunning = true
        ApplicationDispatcher.addChangeListener(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disconnectStateListener() {
        isActivityRunning = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeStateListener() {
        ApplicationDispatcher.removeChangeListener(this)
    }


    /**
     * Use this method to provide lifecylce awareness to this component
     */
    fun registerLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    /**
     * Use this method to subscribe to State changes
     */
//    fun getState(): V {
//        //return ViewState(registrationState = ApplicationDispatcher.registrationStore.state)
//    }

}