package com.gornostaevas.pushify.authorized_list

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.di.ApplicationScope
import javax.inject.Inject

/**
 * Context is necessary to obtain resources from device storage
 */
@ApplicationScope
class AuthorizedListViewModel @Inject constructor(private val context: Context) : ViewModel() {
    // TODO viewModel must get this values used for authorization and visuals FROM repository where

    // this info is saved
    private val allAuthorizedMutable: MutableLiveData<List<AuthorizedEntity>> by lazy {
        MutableLiveData(
            List(20) {
                if (it % 2 == 0) {
                    object : AuthorizedEntity(context) {}
                } else {
                    object : AuthorizedEntity(context) {
                        override fun getImage(): Drawable {
                            return context.getDrawable(R.drawable.ic_facebook)!!
                        }
                    }
                }
            }
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
}
