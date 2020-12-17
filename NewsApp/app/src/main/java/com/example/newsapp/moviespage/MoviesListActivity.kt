package com.example.newsapp.moviespage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.MovieListAdapter
import com.example.newsapp.entity.CityMoviesEntity
import com.example.newsapp.entity.ResultEntity
import com.example.newsapp.http.CustomInterceptor
import com.example.newsapp.service.ApiService
import com.example.newsapp.utils.DateUtils
import kotlinx.android.synthetic.main.activity_movies_list.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesListActivity : AppCompatActivity(),MovieListAdapter.onItemClick{
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
        setContentView(R.layout.activity_movies_list)
        val intent = intent
        title = intent.getStringExtra("name")+"地区"
        val actionBar = supportActionBar//获取AppCompatActivity的默认的actionBar
        //给actionBar添加返回按钮
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        retrofit.getCityMoviesOn(intent.getIntExtra("id",0),intent.getStringExtra("name")!!,DateUtils.stampToTime(System.currentTimeMillis())).enqueue(object : Callback<CityMoviesEntity>{
            override fun onResponse(
                call: Call<CityMoviesEntity>,
                response: Response<CityMoviesEntity>
            ) {
                val list = response.body()?.result?.list
                val adapter = MovieListAdapter(list)
                adapter.setItemClick(this@MoviesListActivity)
                val manager = GridLayoutManager(this@MoviesListActivity,3)
                movieRv.layoutManager = manager
                movieRv.adapter = adapter
            }

            override fun onFailure(call: Call<CityMoviesEntity>, t: Throwable) {
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

    override fun onClick(data: ResultEntity) {
        val intent = Intent(this,MovieDetalsActivity::class.java)
        intent.putExtra("id",data.movieid)
        startActivity(intent)
    }
}