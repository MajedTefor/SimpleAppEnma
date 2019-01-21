package com.enma.app.simple.simpleapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.enma.app.simple.simpleapp.Models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main._item.view.*


class CategoriesRvAdapter(val context: Context): RecyclerView.Adapter<CategoriesRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = (context as AppCompatActivity).layoutInflater.inflate(R.layout._item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.categoryName.text = MainActivity.catsList[position].name
        Picasso.get().load(MainActivity.catsList[position].image).into(holder.categoryIcon)
        holder.parentLL.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return MainActivity.catsList.size
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val categoryName = itemView.catNameTV
        val categoryIcon = itemView.catIconIV
        val parentLL = itemView.parentLL
    }

}