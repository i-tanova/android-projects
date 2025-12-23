package com.tanovait.sunnyapp.data

import java.util.concurrent.atomic.AtomicBoolean

data class OneTimeEvent<T>(val payload: T? = null) {

    private val isConsumed = AtomicBoolean(false)

    internal fun getValue(): T? =
            if (isConsumed.compareAndSet(false, true)) payload
            else null
}

fun <T> T.toOneTimeEvent() =
        OneTimeEvent(this)

/**
 * Allows you to consume the [OneTimeEvent.payload] of the event only once,
 * as it will be marked as consumed on access.
 */

fun <T> OneTimeEvent<T>?.consumeOnce(block: (T) -> Unit) {
    this?.getValue()?.let { block(it) }
}

sealed class Failure {

    /**
     * Extend this class in order to provide your own
     * custom failure.
     */
    open class CustomFailure() : Failure()

    data class UnexpectedFailure(
            val message: String?
    ) : Failure()

}
