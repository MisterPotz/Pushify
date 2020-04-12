package com.gornostaevas.pushify.social_nets.vk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.gornostaevas.pushify.android_utils.setOnMain
import com.gornostaevas.pushify.android_utils.unwrapToString
import com.gornostaevas.pushify.social_nets.AuthorizedClient
import com.gornostaevas.pushify.social_nets.PostData
import com.gornostaevas.pushify.social_nets.PostStatus
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.requests.VKRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber

open class VKClient(val token: AccessToken) : AuthorizedClient {
    private val job = CoroutineScope(Dispatchers.Default)
    override fun sendPost(postData: PostData): LiveData<PostStatus> {
        val requestPostStatus =
            object : VKRequest<VKPostResponse>("wall.post") {
                override fun parse(r: JSONObject): VKPostResponse {
                    return GsonBuilder().create().run {
                        fromJson(r.unwrapToString("response"), VKPostResponse::class.java)
                    }
                }
            }
        requestPostStatus.apply {
            // owner id
            addParam("owner_id", token.userId)
            addParam("friends_only", 1)
            addParam("message", postData.content)
            Timber.v("VK doesn't support titles")
        }
        val liveData = MutableLiveData<PostStatus>()

        VK.execute(
            requestPostStatus,
            object : VKApiCallback<VKPostResponse> {
                override fun fail(error: Exception) {
                    job.launch {
                        liveData.setOnMain(
                            PostStatus(
                                false,
                                null
                            )
                        )
                    }
                }

                override fun success(result: VKPostResponse) {
                    job.launch {
                        liveData.setOnMain(
                            PostStatus(
                                true,
                                null
                            )
                        )
                    }
                }
            })
        return liveData
    }
}