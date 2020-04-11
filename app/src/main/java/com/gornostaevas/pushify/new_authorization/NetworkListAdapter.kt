package com.gornostaevas.pushify.new_authorization

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.social_nets.SupportedNetworks

class NetworkListAdapter(context: Context) :
    ArrayAdapter<SupportedNetworks>(context, R.layout.spinner_item) {
    val array: Array<SupportedNetworks> = SupportedNetworks.values()

    val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, parent)
    }

    override fun getCount(): Int {
        return array.size
    }

    override fun getItem(position: Int): SupportedNetworks? {
        return array[position]
    }

    private fun getView(position: Int, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.spinner_item, parent, false)

        val text = view.findViewById<TextView>(R.id.title)
        text.text = array[position].name
        return view
    }
}