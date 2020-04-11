package com.gornostaevas.pushify

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.gornostaevas.pushify.authorized_list.AuthorizedListViewModel
import kotlinx.android.synthetic.main.apptoolbar.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var viewModel : AuthorizedListViewModel

    // Needs observer as delegate, some lower levels may successfully respond to results of activity
    @Inject lateinit var resultObtainer : ResultObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.i("requestCode: $requestCode, resultCode: $resultCode")
        if (!resultObtainer.obtainResultFromLaunched(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Timber.i("Options created")
        return false
    }
}
