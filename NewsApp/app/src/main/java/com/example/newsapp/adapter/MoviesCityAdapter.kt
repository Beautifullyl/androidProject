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
import com.example.newsapp.entity.Result

class MoviesCityAdapter(private val result: List<Result>?) : RecyclerView.Adapter<MoviesCityAdapter.MyHolder>() {
    private var click : movieCityItemClick? = null

    fun setMovieCityItemClick(click : movieCityItemClick?){
        this.click = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false)
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.cityName.text = result!![position].name
        holder.cityName.setOnClickListener {
            click?.onItemClick(result[position])
        }
    }

    override fun getItemCount(): Int {
        return result?.size!!
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName = itemView.findViewById<TextView>(R.id.movie_city_name)!!
    }

    interface movieCityItemClick{
        fun onItemClick(result: Result)
    }
}