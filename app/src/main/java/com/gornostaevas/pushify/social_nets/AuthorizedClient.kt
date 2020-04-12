package com.gornostaevas.pushify.social_nets

import androidx.lifecycle.LiveData

/**
 * Responsible for operations such as send post and view results from its completion
 */
interface AuthorizedClient {
    /**
     * Sends post asynchronously and returns an observable status
     */
    fun sendPost(postData: PostData) : LiveData<PostStatus>
}