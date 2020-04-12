package com.gornostaevas.pushify.saved_nets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.gornostaevas.pushify.android_utils.setOnMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repository @Inject constructor(private val database: AppDatabase) {
    val scope = CoroutineScope(Dispatchers.IO)

    private val mutableLiveData: MutableLiveData<List<SavedAuthorization>> = MutableLiveData()

    fun getAll(): LiveData<List<SavedAuthorization>> {
        updateData()
        return mutableLiveData
    }

    private fun updateData() {
        scope.launch {
            val list = database.userDao().getAll()
            this@Repository.mutableLiveData.setOnMain(list)
        }
    }

    fun insert(item: SavedAuthorization) {
        scope.launch {
            database.userDao().insertAll(listOf(item))
            updateData()
        }
    }

    fun insert(list: List<SavedAuthorization>) {
        scope.launch {
            database.userDao().insertAll(list)
            updateData()
        }
    }

    fun delete(list: List<SavedAuthorization>) {
        scope.launch {
            database.userDao().delete(list)
            updateData()
        }
    }

    fun delete(item: SavedAuthorization) {
        scope.launch {
            database.userDao().delete(listOf(item))
            updateData()
        }
    }
}