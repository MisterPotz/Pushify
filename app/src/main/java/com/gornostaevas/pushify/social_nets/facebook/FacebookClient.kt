package com.gornostaevas.pushify.social_nets.facebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.gornostaevas.pushify.social_nets.AuthorizedClient
import com.gornostaevas.pushify.social_nets.PostData
import com.gornostaevas.pushify.social_nets.PostStatus
import timber.log.Timber

class FacebookClient() : AuthorizedClient {
    /**
     * Currently facebook sending post is not yet fully supported due to developing serious time limits
     * BUT!
     * The way that sdks are added to the app is isolated from other app
     */
    override fun sendPost(postData: PostData): LiveData<PostStatus> {
        val mutableLiveData = MutableLiveData(PostStatus(false, null))
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        Timber.v("Sending facebook post")
        return mutableLiveData
    }
}