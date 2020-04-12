package com.gornostaevas.pushify.social_nets.vk

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.ResultObserver
import com.gornostaevas.pushify.android_utils.setOnMain
import com.gornostaevas.pushify.android_utils.unwrapToString
import com.gornostaevas.pushify.authorized_list.AuthorizedEntity
import com.gornostaevas.pushify.saved_nets.SavedAuthorization
import com.gornostaevas.pushify.social_nets.*
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
    var count: Int = 0
}

class VKManager : AuthorizationManager {
    val job = CoroutineScope(Dispatchers.Default)

    override fun startAuthentication(
        context: Context,
        activity: Activity,
        resultsObserver: ResultObserver
    ): LiveData<AuthorizedEntity?> {
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
                            Counter.count += 1
                        }
                    })
            }
        }

        VK.login(activity, listOf(VKScope.GROUPS, VKScope.WALL))
        return mutableLiveData
    }

    // All logic of vksdk is located in its activity
    override fun getSpecificFragment(): Fragment? {
        return null
    }

    private fun buildAuthorizedEntity(
        context: Context,
        token: VKAccessToken
    ): CompletableDeferred<AuthorizedEntity> {
        val job = CompletableDeferred<AuthorizedEntity>()


        val requestProfile = object : VKRequest<VKProfileData>("account.getProfileInfo") {
            override fun parse(r: JSONObject): VKProfileData {
                return GsonBuilder().create()
                    .run { fromJson(r.unwrapToString("response"), VKProfileData::class.java) }
            }
        }

        VK.execute(requestProfile, object : VKApiCallback<VKProfileData> {
            override fun fail(error: Exception) {

                val entity = VKEntity(
                    SavedAuthorization(
                        "not loaded",
                        "not loaded",
                        R.drawable.ic_vk,
                        SupportedNetworks.VK.ordinal,
                        token.fromVkAccessToken().toJsonString()
                    )
                )
                job.complete(entity)
            }

            override fun success(result: VKProfileData) {
                val obj = VKEntity(
                    SavedAuthorization(
                        result.first_name + " " + result.last_name,
                        "Place to post: wall",
                        R.drawable.ic_vk,
                        SupportedNetworks.VK.ordinal,
                        token.fromVkAccessToken().toJsonString()
                    )
                )
                job.complete(obj)
            }
        })
        return job
    }
}
