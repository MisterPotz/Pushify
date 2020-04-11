package com.gornostaevas.pushify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.authenticated_social_nets.*

class AuthorizedListFragment : Fragment() {

    companion object {
        fun newInstance() = AuthorizedListFragment()
    }

    private val viewModel by activityViewModels<AuthorizedListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.authorized_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.setContext(context!!)
        authenticatedList.layoutManager = LinearLayoutManager(context)
        authenticatedList.adapter = AuthorizedEntityAdapter(viewLifecycleOwner, viewModel.allAuthorized)
    }
}
