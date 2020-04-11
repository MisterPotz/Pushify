package com.gornostaevas.pushify.authorized_list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gornostaevas.pushify.MyApplication
import com.gornostaevas.pushify.R
import kotlinx.android.synthetic.main.authenticated_social_nets.*
import timber.log.Timber
import javax.inject.Inject

class AuthorizedListFragment : Fragment() {

    companion object {
        fun newInstance() =
            AuthorizedListFragment()
    }



    @Inject
    lateinit var viewModel: AuthorizedListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // necessary to override menu with context-dependent options
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("Authorized list created")
        return inflater.inflate(R.layout.authorized_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity!!.application as MyApplication).appComponent.inject(this)

        authenticatedList.layoutManager = LinearLayoutManager(context)
        authenticatedList.adapter =
            AuthorizedEntityAdapter(
                viewLifecycleOwner,
                viewModel.allAuthorized
            )
    }

    override fun onStart() {
        super.onStart()
        restoreActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.manage_nets, menu)
        Timber.i("Inflated options menu")
    }

    fun restoreActionBar() {
        (activity!! as AppCompatActivity).supportActionBar!!.apply {
            setTitle(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_mainHome_to_authorizeFragment -> findNavController().navigate(R.id.action_mainHome_to_authorizeFragment)
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

}
