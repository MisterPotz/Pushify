package com.gornostaevas.pushify

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * Context is necessary to obtain resources from device storage
 */
class AuthorizedListViewModel : ViewModel() {
    // TODO viewModel must get this values used for authorization and visuals FROM repository where
    private lateinit var context: Context

    // this info is saved
    private val allAuthorizedMutable: MutableLiveData<List<AuthorizedEntity>> by lazy {
        MutableLiveData(
            listOf(object : AuthorizedEntity(context) {}, object : AuthorizedEntity(context) {
                override fun getImage(): Drawable {
                    return context.getDrawable(R.drawable.ic_facebook)!!
                }
            })
        )
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

    fun setContext(context: Context) {
        this.context = context
    }
}
