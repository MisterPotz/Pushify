package com.gornostaevas.pushify.social_nets.facebook

import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.gornostaevas.pushify.R
import kotlinx.android.synthetic.main.fragment_facebook_login.*
import timber.log.Timber


class FacebookLoginFragment(val callback : FacebookCallback<LoginResult?>) : Fragment() {
    val callbackManager by lazy { CallbackManager.Factory.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facebook_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setPermissions(listOf("email","public_profile"))


        // Callback registration
        loginButton.registerCallback(callbackManager, callback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(callback: FacebookCallback<LoginResult?>) = FacebookLoginFragment(callback)
    }
}
