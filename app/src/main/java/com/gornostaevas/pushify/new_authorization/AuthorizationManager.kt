package com.gornostaevas.pushify.new_authorization

import android.app.Activity
import androidx.lifecycle.LiveData
import com.gornostaevas.pushify.ResultObserver

/**
 * Is able to launch new activities (or fragments within nested graph isolated
 * from other app) that are dependent on a social network
 * Hides implementation from client, for each social network there can be multiple implementations.
 *
 * SupportedNetworks -> ordinal -> AuthorizationManager
 */
interface AuthorizationManager {
    /**
     * [resultsObserver] - authorization manager serves as the end-point delegate of activity
     * that it launches.
     */
    fun startAuthentication(activity: Activity, resultsObserver: ResultObserver) : LiveData<AccessData>
}

/**
 * Data that must be used for authorization
 */
interface AccessData