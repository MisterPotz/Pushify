package com.gornostaevas.pushify.new_authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gornostaevas.pushify.MyApplication
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.ResultObserver
import com.gornostaevas.pushify.android_utils.setupToolbar
import com.gornostaevas.pushify.authorized_list.AuthorizedListViewModel
import com.gornostaevas.pushify.di.SocialNetworkComponent
import com.gornostaevas.pushify.di.SocialNetworkModule
import com.gornostaevas.pushify.social_nets.SupportedNetworks
import kotlinx.android.synthetic.main.fragment_authorize.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider


class AuthorizeFragment : Fragment() {
    @Inject
    lateinit var viewModel: AuthorizedListViewModel

    @Inject
    lateinit var resultObtainer: ResultObserver

    @Inject
    lateinit var socialManagerBuilder: Provider<SocialNetworkComponent.Builder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // necessary to override menu with context-dependent options
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authorize, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity!!.application as MyApplication).appComponent.inject(this)

        availableNetworks.adapter = NetworkListAdapter(context!!)
    }

    private var currentAttachedFragment: Fragment? = null

    override fun onStart() {
        super.onStart()

        setupToolbar(R.string.selectNetwork, true)

        authorize.setOnClickListener {
            val manager = socialManagerBuilder.get()
                .socialNetworkModule(SocialNetworkModule(availableNetworks.selectedItem as SupportedNetworks))
                .build()
                .getAuthManager()

            val fragment = manager.getSpecificFragment()
            resultObtainer.cleanCallbacks()

            // TODO this logic is not so good
            if (fragment == null) {
                val liveData = manager.startAuthentication(context!!, activity!!, resultObtainer)
                Timber.i("Live data has observers: ${liveData.hasActiveObservers()}")

                currentAttachedFragment?.let {
                    childFragmentManager.beginTransaction()
                        .remove(it).commit()
                }

                liveData.observe(viewLifecycleOwner, Observer {
                    Timber.i("Got the data")

                    it?.let { viewModel.addNewEntity(it) }
                })
            } else {
                manager.startAuthentication(context!!, activity!!, resultObtainer).observe(viewLifecycleOwner, Observer {
                    Timber.i("Got the data")

                    it?.let { viewModel.addNewEntity(it) }
                })
                childFragmentManager.beginTransaction()
                    .add(R.id.networkSpecificContainer, fragment)
                    .commit()
                currentAttachedFragment = fragment
                Timber.i("Waiting for specific fragment to login")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().popBackStack()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() = AuthorizeFragment()
    }
}
