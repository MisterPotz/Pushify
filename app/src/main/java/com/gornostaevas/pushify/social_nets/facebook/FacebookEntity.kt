package com.gornostaevas.pushify.social_nets.facebook

import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.authorized_list.AuthorizedEntity
import com.gornostaevas.pushify.saved_nets.SavedAuthorization
import com.gornostaevas.pushify.social_nets.AuthorizedClient
import com.gornostaevas.pushify.social_nets.SupportedNetworks

class FacebookEntity(savedAuthorization: SavedAuthorization) :
    AuthorizedEntity(savedAuthorization) {
    override fun getClient(): AuthorizedClient {
        return FacebookClient()
    }

    companion object {
        fun createDefaultSavedAuthorization(): SavedAuthorization {
            return SavedAuthorization(
                "Not yet loaded",
                "Not yet loaded",
                R.drawable.ic_facebook,
                SupportedNetworks.Facebook.ordinal,
                null
            )
        }
    }
}