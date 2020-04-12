package com.gornostaevas.pushify.social_nets

import com.gornostaevas.pushify.authorized_list.AuthorizedEntity
import com.gornostaevas.pushify.saved_nets.SavedAuthorization
import com.gornostaevas.pushify.social_nets.vk.VKEntity
import java.lang.IllegalStateException

object AuthorizationCreator {
    fun createAuthorizationInfo(authorizedEntity: SavedAuthorization) : AuthorizedEntity {
        return when(authorizedEntity.networkType.run { SupportedNetworks.values()[this] }) {
            SupportedNetworks.VK -> VKEntity(authorizedEntity)
            else -> throw IllegalStateException("Not implemented yet")
        }
    }
}