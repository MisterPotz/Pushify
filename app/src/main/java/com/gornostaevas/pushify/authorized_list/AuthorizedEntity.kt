package com.gornostaevas.pushify.authorized_list

import android.content.Context
import android.graphics.drawable.Drawable
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.social_nets.AuthorizedClient

/**
 * Contains all specific data that is used by authentication APIs of various social networks.
 * The data is implementation specific, each service uses its own way of registering API,
 * so interface here is a top declaration for all entities, that contain relevant data.
 *
 * But methods that MUST be implemented are for visual information
 */
abstract class AuthorizedEntity constructor(val context: Context) {
    open fun getTitle() : String = "Some title"

    open fun getInfo() : String = "Some info"

    open fun getImage() : Drawable = context.getDrawable(R.drawable.ic_vk)!!

    // Sometimes connection can go off - in that case, we can understand if this entity requires some inner reloading
    fun isLoaded() : Boolean = true

    // Reload inner component
    fun reload() : Unit = Unit

    abstract fun getClient() : AuthorizedClient
}