package com.gornostaevas.pushify.authorized_list

import android.content.Context
import androidx.lifecycle.*
import com.gornostaevas.pushify.di.ApplicationScope
import com.gornostaevas.pushify.saved_nets.Repository
import com.gornostaevas.pushify.social_nets.AuthorizationCreator
import com.gornostaevas.pushify.social_nets.PostData
import com.gornostaevas.pushify.social_nets.PostStatus
import timber.log.Timber
import javax.inject.Inject

/**
 * Context is necessary to obtain resources from device storage
 */
@ApplicationScope
class AuthorizedListViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    // TODO viewModel must get this values used for authorization and visuals FROM repository where

    // this info is saved
    private val allAuthorizedMutable: LiveData<List<AuthorizedEntity>> = Transformations.switchMap(
        repository.getAll()
    ) {
        Timber.i("in switchMap of allAuthorizedMutable")
        MutableLiveData(it.map {
            AuthorizationCreator.createAuthorizationInfo(it)
        })
    }

    val allAuthorized: LiveData<List<AuthorizedEntity>>
        get() = Transformations.switchMap(
            allAuthorizedMutable
        ) {
            Timber.i("in switchMap of allAuthorized")
            MutableLiveData(it)
        }

    fun addNewEntity(entity: AuthorizedEntity) {
        repository.insert(entity.savedAuthorization)
    }

    private fun createMediatorDataUniter(eventsToObserve : List<LiveData<PostStatus>>) : LiveData<List<Pair<AuthorizedEntity,PostStatus>>>  {
        val mediatorLiveData = MediatorLiveData<List<Pair<AuthorizedEntity,PostStatus>>>()
        val mutableList = mutableListOf<Pair<AuthorizedEntity,PostStatus>>()
        eventsToObserve.forEachIndexed { index, item ->
            mediatorLiveData.addSource(item) {
                mutableList.add(index, Pair( allAuthorized.value!![index],it))

                // Если список пополнился настолько же, сколько есть в оригинальном - наверное пора
                // излучить его наблюдателям
                if (mutableList.size >= eventsToObserve.size) {
                    mediatorLiveData.value = mutableList
                }
            }
        }
        return mediatorLiveData
    }

    var latestResults :LiveData<List<Pair<AuthorizedEntity,PostStatus>>>? = null

    fun sendPostToAll(postData: PostData): LiveData<List<Pair<AuthorizedEntity,PostStatus>>> {
        val mappedDatas = allAuthorizedMutable.value!!.map {
            it.getClient().sendPost(postData)
            // Здесь отдать список лайв дат, из которых будет строиться результат
        }
        return createMediatorDataUniter(mappedDatas).apply { latestResults = this }
    }
}
