package com.enma.app.simple.simpleapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main._card.view.*
import kotlinx.android.synthetic.main._item.view.*

class PostsRvAdapter(private val context: Context): RecyclerView.Adapter<PostsRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = (context as AppCompatActivity).layoutInflater.inflate(R.layout._card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.postTitle.text = FeedsActivity.postsList[position].caption
        holder.likesCount.text = FeedsActivity.postsList[position].like_count
        holder.commentsCount.text = FeedsActivity.postsList[position].comment_count
        holder.writerName.text = FeedsActivity.postsList[position].name
        Picasso.get().load(FeedsActivity.postsList[position].media[1].url).into(holder.postImg)

    }

    override fun getItemCount(): Int {
        return FeedsActivity.postsList.size
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val postImg = itemView.postImgIV
        val postTitle = itemView.postTitleTV
        val writerName = itemView.writerNameTV
        val writerImg = itemView.writerImgIV
        val likesCount = itemView.likesTV
        val commentsCount = itemView.commentsTV

    }

}