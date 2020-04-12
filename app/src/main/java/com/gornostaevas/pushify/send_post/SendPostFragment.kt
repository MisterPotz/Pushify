package com.gornostaevas.pushify.send_post

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gornostaevas.pushify.MyApplication

import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.android_utils.setupToolbar
import com.gornostaevas.pushify.authorized_list.AuthorizedListViewModel
import com.gornostaevas.pushify.social_nets.PostData
import kotlinx.android.synthetic.main.fragment_send_post.*
import javax.inject.Inject

class SendPostFragment : Fragment() {

    @Inject
    lateinit var viewModel: AuthorizedListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // necessary for customizing options menu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_post, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity!!.application as MyApplication).appComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        setupToolbar(R.string.send_post, true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.send_post, menu)
    }

    private fun getPostData(): PostData {
        return PostData(title.text.toString(), content.text.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> findNavController().popBackStack()
            R.id.sendPost -> viewModel.sendPostToAll(getPostData()).let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SendPostFragment()
    }
}
