package com.gornostaevas.pushify.social_nets

import android.media.FaceDetector
import com.gornostaevas.pushify.authorized_list.AuthorizedEntity
import com.gornostaevas.pushify.saved_nets.SavedAuthorization
import com.gornostaevas.pushify.social_nets.facebook.FacebookEntity
import com.gornostaevas.pushify.social_nets.vk.VKEntity
import java.lang.IllegalStateException

object AuthorizationCreator {
    fun createAuthorizationInfo(savedAuthorization: SavedAuthorization) : AuthorizedEntity {
        return when(savedAuthorization.networkType.run { SupportedNetworks.values()[this] }) {
            SupportedNetworks.VK -> VKEntity(savedAuthorization)
            SupportedNetworks.Facebook -> FacebookEntity(savedAuthorization)
            else -> throw IllegalStateException("Not implemented yet")
        }
    }
}