package com.gornostaevas.pushify.social_nets.vk

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.ResultObserver
import com.gornostaevas.pushify.android_utils.setOnMain
import com.gornostaevas.pushify.android_utils.unwrapToString
import com.gornostaevas.pushify.authorized_list.AuthorizedEntity
import com.gornostaevas.pushify.social_nets.AuthorizationManager
import com.gornostaevas.pushify.social_nets.AuthorizedClient
import com.gornostaevas.pushify.social_nets.PostData
import com.gornostaevas.pushify.social_nets.PostStatus
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.requests.VKRequest
import kotlinx.coroutines.*
import org.json.JSONObject
import timber.log.Timber
import java.lang.IllegalStateException

object Counter {
    var count : Int = 0
}

class VKManager : AuthorizationManager {
    val job = CoroutineScope(Dispatchers.Default)

    override fun startAuthentication(
        context: Context,
        activity: Activity,
        resultsObserver: ResultObserver
    ): LiveData<AuthorizedEntity> {
        val mutableLiveData = MutableLiveData<AuthorizedEntity>()
        resultsObserver.registerCallback {
            it.run {
                VK.onActivityResult(
                    requestCode,
                    resultCode,
                    data,
                    object : VKAuthCallback {
                        override fun onLogin(token: VKAccessToken) {
                            Timber.v("Successfully logged in")
//                            buildAuthorizedEntity(context, token).observeForever {
//                                Timber.v("Got authorization entity in VK")
//                                mutableLiveData.value = i
//                            }
                            val task = buildAuthorizedEntity(context, token)
                            job.launch {
                                val value = task.await()
                                mutableLiveData.setOnMain(value)
                            }
                        }

                        override fun onLoginFailed(errorCode: Int) {
                            Timber.w("Failed to logged in ${Counter.count}")
                            Timber.w("This vk manager: ${this@VKManager}}")
                            Counter.count +=1
                        }
                    })
            }
        }

        VK.login(activity, listOf(VKScope.GROUPS, VKScope.WALL))
        return mutableLiveData
    }

    private fun buildAuthorizedEntity(
        context: Context,
        token: VKAccessToken
    ): CompletableDeferred<AuthorizedEntity> {
        var title: String? = null
        var info: String? = null

        val job = CompletableDeferred<AuthorizedEntity>()


        val requestProfile = object : VKRequest<VKProfileData>("account.getProfileInfo") {
            override fun parse(r: JSONObject): VKProfileData {
                return GsonBuilder().create()
                    .run { fromJson(r.unwrapToString("response"), VKProfileData::class.java) }
            }
        }

        VK.execute(requestProfile, object : VKApiCallback<VKProfileData> {
            override fun fail(error: Exception) {
                val entity = object : AuthorizedEntity(context) {
                    // Здесь нужно зделать корутину (или уровнем выше), чтобы получить имя юзера
                    override fun getTitle(): String {
                        return "Not loaded"
                    }

                    override fun getInfo(): String {
                        return "Not loaded"
                    }

                    override fun getImage(): Drawable {
                        return context.getDrawable(R.drawable.ic_vk)!!
                    }

                    override fun getClient(): AuthorizedClient {
                        throw IllegalStateException("Can't use authorized client - initilization of entity was not complete")
                    }
                }
                job.complete(entity)
            }

            override fun success(result: VKProfileData) {
                val obj = object : AuthorizedEntity(context) {
                    override fun getTitle(): String {
                        return result.first_name + " " + result.last_name
                    }

                    override fun getInfo(): String {
                        return "Wall"
                    }

                    override fun getImage(): Drawable {
                        return context.getDrawable(R.drawable.ic_vk)!!
                    }

                    override fun getClient(): AuthorizedClient {
                        // First, we must obtain info about user
                        return object :
                            AuthorizedClient {
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
                                            this@VKManager.job.launch {
                                                liveData.setOnMain(
                                                    PostStatus(
                                                        false,
                                                        null
                                                    )
                                                )
                                            }
                                        }

                                        override fun success(result: VKPostResponse) {
                                            this@VKManager.job.launch {
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
                    }
                }
                job.complete(obj)
            }
        })
        return job
    }
}
