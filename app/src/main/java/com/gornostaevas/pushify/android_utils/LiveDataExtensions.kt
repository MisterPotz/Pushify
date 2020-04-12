package com.gornostaevas.pushify.android_utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> MutableLiveData<T>.setOnMain(value : T) {
    withContext(Dispatchers.Main) {
        setValue(value)
    }
}