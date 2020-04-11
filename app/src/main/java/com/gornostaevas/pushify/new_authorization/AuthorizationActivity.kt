package com.gornostaevas.pushify.new_authorization

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.social_nets.SupportedNetworks
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/*
class AuthorizationActivity : AppCompatActivity(), CoroutineScope {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        val activityInfo = intent.getIntExtra(AuthenticationRequest.networkType, 0)
        val text = code.text.toString() + activityInfo
        code.text = text
        // create own navController
        val navController = findNavController(R.id.authorizationNavHost).apply {
            setGraph(R.navigation.authorization_graph)
        }
        launch {
            // wait 3 second before going back
            delay(3000)
            runOnUiThread {
                */
/* val action = AuthorizeNavigationDirections.actionGlobalAuthorizeFragment(
                     AuthorizationInfo(SupportedNetworks.VK, null)
                 )*//*

                // stores autharization info
                val info = AuthorizationInfo(SupportedNetworks.VK, null)
                val intent = Intent().apply { putExtra("someParcelable", info) }
                setResult(200, intent)

                finish()
                // doesn't funcking work goddamit, never trust google alpha versions shitnfuck
                // navController.navigate(action)
            }
        }
    }

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, AuthorizationActivity::class.java)
        }
    }
}
*/
