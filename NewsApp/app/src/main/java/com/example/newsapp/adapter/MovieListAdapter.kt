package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.app.MyApplication
import com.example.newsapp.entity.ResultEntity

class MovieListAdapter(private val list : List<ResultEntity>?) : RecyclerView.Adapter<MovieListAdapter.MyHolder>() {
    private var itemClick : onItemClick? = null

    fun setItemClick(itemClick : onItemClick?){
        this.itemClick = itemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_city_item_layout, parent, false)
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(MyApplication.getInstance()!!)
            .load(list!![position].pic)
            .into(holder.coverImg)
        holder.name.text = list[position].moviename

        holder.parent.setOnClickListener {
            itemClick?.onClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val coverImg = itemView.findViewById<ImageView>(R.id.movies_cover_img)
        val name = itemView.findViewById<TextView>(R.id.movies_name)
        val parent = itemView.findViewById<ConstraintLayout>(R.id.movie_parent_layout)
    }

    interface onItemClick{
        fun onClick(data : ResultEntity)
    }
}