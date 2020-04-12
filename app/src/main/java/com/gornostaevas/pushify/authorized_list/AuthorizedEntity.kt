package com.gornostaevas.pushify.authorized_list

import androidx.annotation.DrawableRes
import com.gornostaevas.pushify.saved_nets.SavedAuthorization
import com.gornostaevas.pushify.social_nets.AuthorizedClient

/**
 * Contains all specific data that is used by authentication APIs of various social networks.
 * The data is implementation specific, each service uses its own way of registering API,
 * so interface here is a top declaration for all entities, that contain relevant data.
 *
 * But methods that MUST be implemented are for visual information
 */
abstract class AuthorizedEntity constructor(val savedAuthorization: SavedAuthorization) {
    open fun getTitle() : String = savedAuthorization.title

    open fun getInfo() : String = savedAuthorization.adiitional

    @DrawableRes
    open fun getImage() : Int = savedAuthorization.drawable

    // Sometimes connection can go off - in that case, we can understand if this entity requires some inner reloading
    fun isLoaded() : Boolean = true

    // Reload inner component
    fun reload() : Unit = Unit

    abstract fun getClient() : AuthorizedClient
}