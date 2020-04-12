package com.gornostaevas.pushify.social_nets

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import com.gornostaevas.pushify.ResultObserver
import com.gornostaevas.pushify.authorized_list.AuthorizedEntity

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
    fun startAuthentication(context: Context, activity: Activity, resultsObserver: ResultObserver) : LiveData<AuthorizedEntity>
}

/**
 * Data that must be used for authorization
 */
interface AccessData