package com.gornostaevas.pushify.social_nets

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gornostaevas.pushify.ResultObserver
import com.gornostaevas.pushify.new_authorization.AccessData
import com.gornostaevas.pushify.new_authorization.AuthorizationManager
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import timber.log.Timber

data class someVk(val accessToken: String) : AccessData

class VKManager : AuthorizationManager {
    override fun startAuthentication(activity: Activity, resultsObserver: ResultObserver) : LiveData<AccessData> {
        val liveData = MutableLiveData<AccessData>()
        resultsObserver.registerCallback {
            it.run {
                VK.onActivityResult(
                    requestCode,
                    resultCode,
                    data,
                    object : VKAuthCallback {
                        override fun onLogin(token: VKAccessToken) {
                            Timber.i("Successfully logged in")
                            liveData.value = someVk(token.accessToken)
                        }

                        override fun onLoginFailed(errorCode: Int) {
                            Timber.w("Failed to logged in")
                        }
                    })
            }
        }
        VK.login(activity)
        return liveData
    }
}