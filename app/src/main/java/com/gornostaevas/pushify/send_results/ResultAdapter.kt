package com.gornostaevas.pushify.send_results

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.gornostaevas.pushify.R
import com.gornostaevas.pushify.authorized_list.AuthorizedEntity
import com.gornostaevas.pushify.social_nets.PostStatus
import kotlinx.android.synthetic.main.item_post_result.view.*

class ResultAdapter(
    val context: Context,
    val itemsList : LiveData<List<Pair<AuthorizedEntity,PostStatus>>>
    ) : RecyclerView.Adapter<ResultViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.item_post_result, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsList.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.image.setImageDrawable(context.getDrawable(itemsList.value!![position].first.getImage())!!)
        val status = if (itemsList.value!![position].second.postStatus) "OK" else "Fail"
        holder.statusText.text = "Status of sending: ${status}"
    }
}

class ResultViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val image = itemView.findViewById<ImageView>(R.id.image)
    val statusText = itemView.findViewById<TextView>(R.id.textStatus)
}