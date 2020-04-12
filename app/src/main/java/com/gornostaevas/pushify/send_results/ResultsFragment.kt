package com.gornostaevas.pushify.send_results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gornostaevas.pushify.MyApplication

import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.android_utils.setupToolbar
import com.gornostaevas.pushify.authorized_list.AuthorizedListViewModel
import kotlinx.android.synthetic.main.fragment_results.*
import javax.inject.Inject


class ResultsFragment : Fragment() {
    @Inject lateinit var viewModel: AuthorizedListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity!!.application as MyApplication).appComponent.inject(this)
        setupToolbar(R.string.status, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupToolbar(R.string.send_post, true)
        list_item.layoutManager = LinearLayoutManager(context!!)
        list_item.adapter = ResultAdapter(context!!, viewModel.latestResults!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultsFragment()
    }
}
