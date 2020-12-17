package com.example.newsapp.moviespage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.MoviesCityAdapter
import com.example.newsapp.entity.MoviesEntity
import com.example.newsapp.entity.Result
import com.example.newsapp.http.CustomInterceptor
import com.example.newsapp.service.ApiService
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MovieFragment :Fragment(),MoviesCityAdapter.movieCityItemClick{
    private var movieRv : RecyclerView? = null

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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_movie, container, false)
        movieRv = inflate.findViewById(R.id.movieRecyclerview)
        loadMovies()
        return inflate
    }

    private fun loadMovies(){
        retrofit.getCityMovies(0).enqueue(object : Callback<MoviesEntity>{
            override fun onResponse(call: Call<MoviesEntity>, response: Response<MoviesEntity>) {
                val result = response.body()?.result
                val adapter = MoviesCityAdapter(result)
                adapter.setMovieCityItemClick(this@MovieFragment)
                val layoutManager = GridLayoutManager(context,3)
                movieRv?.layoutManager = layoutManager
                movieRv?.adapter = adapter
            }

            override fun onFailure(call: Call<MoviesEntity>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


    }

    override fun onItemClick(result: Result) {
        val intent = Intent(activity,MoviesListActivity::class.java)
        intent.putExtra("id",result.cityid)
        intent.putExtra("name",result.name)
        startActivity(intent)
    }
}

