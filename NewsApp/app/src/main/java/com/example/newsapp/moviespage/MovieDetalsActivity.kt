package com.example.newsapp.moviespage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IntegerRes
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.app.MyApplication
import com.example.newsapp.entity.MovieDetailEntity
import com.example.newsapp.http.CustomInterceptor
import com.example.newsapp.service.ApiService
import kotlinx.android.synthetic.main.activity_movie_detals.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDetalsActivity : AppCompatActivity() {
    private val retrofit by lazy {
        Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jisuapimovie.api.bdymkt.com/movie/")
            .build()
            .create(ApiService::class.java)
    }
    private val builder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder().addInterceptor(CustomInterceptor())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detals)
        title = "电影详情"
        val actionBar = supportActionBar//获取AppCompatActivity的默认的actionBar
        //给actionBar添加返回按钮
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val intent = intent
        val id = intent.getStringExtra("id")

        retrofit.getMovieDetails(id!!.toInt()).enqueue(object : retrofit2.Callback<MovieDetailEntity>{
            override fun onResponse(
                call: Call<MovieDetailEntity>,
                response: Response<MovieDetailEntity>
            ) {
                val data = response.body()?.result
                Glide.with(MyApplication.getInstance()!!)
                    .load(data?.pic)
                    .into(img_cover)
                 if(data?.moviename!!.isNotEmpty()) movie_name.text ="电影名称："+data?.moviename else ""
                year_text.text = "电影年份："+data?.year
                release_date.text = "上映时间："+data?.releasedate
                country.text = "国家："+data?.country
                screenwriter.text = "作者："+data?.screenwriter
                duration.text = "电影时长："+data?.duration
            }

            override fun onFailure(call: Call<MovieDetailEntity>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}