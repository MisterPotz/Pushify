package com.gornostaevas.pushify.saved_nets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.gornostaevas.pushify.android_utils.setOnMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repository @Inject constructor(val database: AppDatabase) {
    val scope = CoroutineScope(Dispatchers.IO)

    private val mutableLiveData: MutableLiveData<List<SavedAuthorization>> = MutableLiveData()

    private val liveData : LiveData<List<SavedAuthorization>> = Transformations.switchMap(mutableLiveData) {
        MutableLiveData(it)
    }

    fun getAll(): LiveData<List<SavedAuthorization>> {
        scope.launch {
            val list = database.userDao().getAll()
            this@Repository.mutableLiveData.setOnMain(list)
        }
        return liveData
    }

    fun insert(item: SavedAuthorization) {
        scope.launch {
            database.userDao().insertAll(listOf(item))
            getAll()
        }
    }

    fun insert(list: List<SavedAuthorization>) {
        scope.launch {
            database.userDao().insertAll(list)
            getAll()
        }
    }

    fun delete(list: List<SavedAuthorization>) {
        scope.launch {
            database.userDao().delete(list)
            getAll()
        }
    }

    fun delete(item: SavedAuthorization) {
        scope.launch {
            database.userDao().delete(listOf(item))
            getAll()
        }
    }
}