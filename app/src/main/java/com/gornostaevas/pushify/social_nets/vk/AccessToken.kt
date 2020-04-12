package com.gornostaevas.pushify.social_nets.vk

import com.google.gson.GsonBuilder
import com.vk.api.sdk.auth.VKAccessToken

/**
 * Used to store in db
 */
data class AccessToken(
    val userId: Int,
    val accessToken: String,
    val secret: String?,
    val created: Long,
    val email: String?,
    val phone: String?,
    val phoneAccessKey: String?
) {

    companion object {


    }
}

fun AccessToken.toJsonString(): String {
    val gson = GsonBuilder().create()
    return gson.toJson(this)
}

fun VKAccessToken.fromVkAccessToken(): AccessToken {
    return AccessToken(userId, accessToken, secret, created, email, phone, phoneAccessKey)
}
