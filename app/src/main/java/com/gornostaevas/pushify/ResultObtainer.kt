package com.gornostaevas.pushify

import android.content.Intent
import timber.log.Timber

/**
 * Its purpose to delegate results coming to main activity from other activities.
 * It may seem irration at first but this is necessary evil due to:
 * 1) Limitations of native android framework for navigation (it doesnt allow returning to original
 * navigation graph from the different activity)
 * 2) And I need it to come from different activities because some social networks have SDK activities
 * which can be reused.
 * With using DI this interface wont be that bad.
 */
interface ResultObserver {
    fun obtainResultFromLaunched(requestCode: Int, resultCode: Int, data: Intent?): Boolean

    fun registerCallback(callback: (ActivityResultEvent) -> Boolean)

    fun cleanCallbacks()
}

data class ActivityResultEvent(
    val requestCode: Int,
    val resultCode: Int,
    val data: Intent?
)

class ResultObserverImpl : ResultObserver {
    /**
     *  Clients can add their callbacks on multiple hierarchies. If no client's callback returns
     *  true - activity will use its super methods
     */
    private val callbacks: MutableList<(ActivityResultEvent) -> Boolean> = mutableListOf()

    override fun obtainResultFromLaunched(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ): Boolean {
        val resultEvent = ActivityResultEvent(requestCode, resultCode, data)
        val successful = callbacks.map { it(resultEvent) }.filter { it }

        cleanCallbacks()

        if (successful.size > 1) {
            Timber.w("Multiple successful executions among callbacks")
            return true
        }
        if (successful.isEmpty()) {
            return false
        }
        return true
    }

    override fun cleanCallbacks() {
        callbacks.removeAll { true }
    }

    override fun registerCallback(callback: (ActivityResultEvent) -> Boolean) {
        callbacks.add(callback)
    }
}