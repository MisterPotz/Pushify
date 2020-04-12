package com.gornostaevas.pushify.authorized_list

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.di.ApplicationScope
import com.gornostaevas.pushify.social_nets.AuthorizedClient
import com.gornostaevas.pushify.social_nets.PostData
import com.gornostaevas.pushify.social_nets.PostStatus
import javax.inject.Inject

/**
 * Context is necessary to obtain resources from device storage
 */
@ApplicationScope
class AuthorizedListViewModel @Inject constructor(private val context: Context) : ViewModel() {
    // TODO viewModel must get this values used for authorization and visuals FROM repository where

    // this info is saved
    private val allAuthorizedMutable: MutableLiveData<List<AuthorizedEntity>> by lazy {
        MutableLiveData<List<AuthorizedEntity>>(mutableListOf())
    }

    val allAuthorized: LiveData<List<AuthorizedEntity>>
        get() = Transformations.switchMap(allAuthorizedMutable) {
            MutableLiveData(it)
        }

    fun addNewEntity(entity: AuthorizedEntity) {
        allAuthorizedMutable.value = allAuthorizedMutable.value!!.toMutableList().apply {
            add(entity)
        }
    }

    fun sendPostToAll(postData: PostData) : List<LiveData<PostStatus>> {
        return allAuthorizedMutable.value!!.map {
            it.getClient().sendPost(postData)
                // Здесь отдать список лайв дат, из которых будет строиться результат
        }
    }
}
