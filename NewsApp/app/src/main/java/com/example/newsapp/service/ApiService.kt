package com.example.newsapp.service

import com.example.newsapp.entity.*
import com.example.newsapp.http.RetrofitClient
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {
    companion object{
       fun instance():ApiService?{
           return RetrofitClient?.instance?.retrofit?.create(ApiService::class.java)
       }
    }

    @POST("index")
    fun getNews(@Query("type") type:String?,@Query("key") key:String?) : Observable<NewsEntity>?


    @POST("city")
    fun getCityMovies(@Query("parentid")id : Int) : Call<MoviesEntity>

    @POST("on")
    fun  getCityMoviesOn(@Query("cityid")cityid : Int,@Query("city")city: String,@Query("date")date:String) : Call<CityMoviesEntity>

    @POST("detail")
    fun getMovieDetails(@Query("movieid")movieid : Int) : Call<MovieDetailEntity>

}