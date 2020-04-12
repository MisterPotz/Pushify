package com.gornostaevas.pushify.social_nets.vk

import com.google.gson.GsonBuilder
import com.gornostaevas.pushify.authorized_list.AuthorizedEntity
import com.gornostaevas.pushify.saved_nets.SavedAuthorization
import com.gornostaevas.pushify.social_nets.AuthorizedClient

class VKEntity(savedAuthorization: SavedAuthorization) : AuthorizedEntity(savedAuthorization) {
    private var client: VKClient = VKClient(unparseToken())

    constructor(
        savedAuthorization: SavedAuthorization,
        client: VKClient
    ) : this(savedAuthorization) {
        this.client = client
    }

    override fun getClient(): AuthorizedClient {
        // First, we must obtain current token
        return client
    }

    private fun unparseToken(): AccessToken {
        val gson = GsonBuilder().create()
        return gson.fromJson(savedAuthorization.data, AccessToken::class.java)
    }
}