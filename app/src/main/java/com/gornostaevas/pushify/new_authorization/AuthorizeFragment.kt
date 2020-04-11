package com.gornostaevas.pushify.new_authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gornostaevas.pushify.MyApplication
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.ResultObserver
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

    @Inject lateinit var socialManagerBuilder : Provider<SocialNetworkComponent.Builder>

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
        /*       ArrayAdapter()(
                   this,
                   R.array.planets_array,
                   android.R.layout.simple_spinner_item
               ).also { adapter ->
                   // Specify the layout to use when the list of choices appears
                   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                   // Apply the adapter to the spinner
                   spinner.adapter = adapter
               } */
    }

    override fun onStart() {
        super.onStart()
        (activity!! as AppCompatActivity).supportActionBar!!.setTitle(R.string.selectNetwork)
        (activity!! as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // TODO get authorizationmanager via dagger - via network

        authorize.setOnClickListener {
            val manager = socialManagerBuilder.get().socialNetworkModule(SocialNetworkModule(SupportedNetworks.VK)).build().getAuthManager()

            manager.startAuthentication(activity!!, resultObtainer).observe(viewLifecycleOwner, Observer {
                Timber.i("Got the data")
            })
        }
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
