package com.gornostaevas.pushify.authorized_list

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.gornostaevas.pushify.R
import timber.log.Timber

/**
 * Responsible for visualizing list of authorized items
 */
class AuthorizedEntityAdapter(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    val list: LiveData<List<AuthorizedEntity>>
) : RecyclerView.Adapter<AuthorizedEntityAdapter.AuthorizedEntityHolder>() {

    var drawablesList: List<Drawable> = listOf()

    init {
        list.observe(lifecycleOwner, Observer {
            Timber.i("in init of observation")
            drawablesList = it.map { context.getDrawable(it.getImage())!! }
            notifyDataSetChanged()
        })
    }

    class AuthorizedEntityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.itemIcon)
        val title = itemView.findViewById<TextView>(R.id.itemTitle)
        val info = itemView.findViewById<TextView>(R.id.itemInfo)

        fun bindTo(entity: AuthorizedEntity, drawable: Drawable) {
            image.setImageDrawable(drawable)
            title.text = entity.getTitle()
            info.text = entity.getInfo()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorizedEntityHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_social_net, parent, false)
        return AuthorizedEntityHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: AuthorizedEntityHolder, position: Int) {
        val somre = drawablesList
        val somre2 = list.value
        Timber.i("Count in recycler: drawables - $somre, list - $somre2")
        holder.bindTo(list.value!![position], drawablesList!![position])
    }
}