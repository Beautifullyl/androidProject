package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.app.MyApplication
import com.example.newsapp.entity.DataBean

class NewsRvAdapter(private val list: List<DataBean>?) : RecyclerView.Adapter<NewsRvAdapter.MyHolder>() {
    private var itemClick : newsItemClick? = null

    public fun setItemClick(itemClick: newsItemClick){
        this.itemClick = itemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val news =list!![position]
        holder.newsAuthor.text = news.author_name
        holder.newsDate.text = news.date
        holder.newsTitle.text = news.title
        Glide.with(MyApplication.getInstance()!!)
            .load(news.thumbnail_pic_s)
            .into(holder.newsImage)
        holder.parent.setOnClickListener {
            itemClick?.onNewsItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    class MyHolder(view: View) : RecyclerView.ViewHolder(view){
        val newsImage: ImageView =view.findViewById(R.id.newsImage)
        val newsDate: TextView =view.findViewById(R.id.newsDate)
        val newsAuthor: TextView =view.findViewById(R.id.newsAuthor)
        val newsTitle: TextView =view.findViewById(R.id.newsTitle)
        val parent : LinearLayout = view.findViewById(R.id.news_parent)
    }

    interface newsItemClick{
        fun onNewsItemClick(dataBean: DataBean)
    }

}