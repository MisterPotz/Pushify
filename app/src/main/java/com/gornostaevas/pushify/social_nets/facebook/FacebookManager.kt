package com.gornostaevas.pushify.social_nets.facebook

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.google.gson.GsonBuilder
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.ResultObserver
import com.gornostaevas.pushify.android_utils.setOnMain
import com.gornostaevas.pushify.authorized_list.AuthorizedEntity
import com.gornostaevas.pushify.saved_nets.SavedAuthorization
import com.gornostaevas.pushify.social_nets.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class FacebookManager : AuthorizationManager {
    override fun startAuthentication(
        context: Context,
        activity: Activity,
        resultsObserver: ResultObserver
    ): LiveData<AuthorizedEntity?> {
        this.context = context
        // using current facebook login button, we can set it to work
        val mutableLiveData = Transformations.switchMap(liveData) {
            MutableLiveData(it)
        }
        resultsObserver.registerCallback {
            it.run {
                Timber.v("Obtained result")
                savedFragment.callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }

        return mutableLiveData
    }

    private val scope = CoroutineScope(Dispatchers.Default)

    private val task: CompletableDeferred<AuthorizedEntity?> by lazy { CompletableDeferred<AuthorizedEntity?>() }
    private val liveData: MutableLiveData<AuthorizedEntity?> by lazy { MutableLiveData<AuthorizedEntity?>() }

    private lateinit var context: Context

    private var callbackForLogin = object : FacebookCallback<LoginResult?> {
        override fun onSuccess(loginResult: LoginResult?) {
            // App code
            // TODO УДАЛИТЬ ПОТОМ ЭТОТ ЛОГ
            Timber.i("Successfully logged in FB, loginResult : $loginResult")
            scope.launch {
                val completed = createEntity(context, loginResult!!).await()
                task.complete(completed)
                liveData.setOnMain(completed)
            }
        }

        override fun onCancel() {
            // App code
            task.complete(null)
            liveData.value = null
            Timber.i("Cancelled log in FB")
        }

        override fun onError(exception: FacebookException) {
            // App code
            task.complete(null)
            liveData.value = null
            Timber.i("Error while log in FB")
        }
    }

    private val savedFragment: FacebookLoginFragment by lazy {
        FacebookLoginFragment.newInstance(
            callbackForLogin
        )
    }

    override fun getSpecificFragment(): Fragment? {
        Timber.i("Returned predefined fragment")
        return savedFragment
    }

    private fun createEntity(
        context: Context,
        loginResult: LoginResult
    ): CompletableDeferred<AuthorizedEntity> {
        val newTask: CompletableDeferred<AuthorizedEntity> = CompletableDeferred()
        // must make request to obtain user data
        val request = GraphRequest.newMeRequest(loginResult.accessToken) { json, _ ->
            val gson = GsonBuilder().create()
            val data = gson.fromJson(json.toString(), FBProfile::class.java)

            newTask.apply {
                val obj = data.run {
                    FacebookEntity(
                        SavedAuthorization(
                            first_name + " " + last_name,
                            "Place to post: Wall",
                            R.drawable.ic_facebook,
                            SupportedNetworks.Facebook.ordinal,
                            null
                        )
                    )
                }
                complete(obj)
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,first_name,last_name,link")
        request.parameters = parameters
        request.executeAsync()
        return newTask
    }
}