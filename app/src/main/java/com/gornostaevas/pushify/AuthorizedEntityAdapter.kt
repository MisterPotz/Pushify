package com.gornostaevas.pushify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

/**
 * Responsible for visualizing list of authorized items
 */
class AuthorizedEntityAdapter(
    lifecycleOwner: LifecycleOwner,
    val list: LiveData<List<AuthorizedEntity>>) :
    RecyclerView.Adapter<AuthorizedEntityAdapter.AuthorizedEntityHolder>() {

    init {
        list.observe(lifecycleOwner, Observer {
            notifyDataSetChanged()
        })
    }

    class AuthorizedEntityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.itemIcon)
        val title = itemView.findViewById<TextView>(R.id.itemTitle)
        val info = itemView.findViewById<TextView>(R.id.itemInfo)

        fun bindTo(entity : AuthorizedEntity) {
            image.setImageDrawable(entity.getImage())
            title.text = entity.getTitle()
            info.text = entity.getInfo()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorizedEntityHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_social_net, parent, false)
        return AuthorizedEntityHolder(view)
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    override fun onBindViewHolder(holder: AuthorizedEntityHolder, position: Int) {
        holder.bindTo(list.value!![position])
    }
}